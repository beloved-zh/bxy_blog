package com.zh.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zh.VO.ResultVO;
import com.zh.pojo.Role;
import com.zh.pojo.User;
import com.zh.pojo.UserRole;
import com.zh.service.RoleService;
import com.zh.service.UserRoleService;
import com.zh.service.UserService;
import com.zh.utils.*;
import io.swagger.annotations.Api;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthQqRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Beloved
 * @date 2020/8/9 22:07
 */
@RestController
@RequestMapping("/oauth")
@Api(value = "第三方登录相关接口", tags = {"第三方登录相关接口"})
public class AuthController {

    @Value(value = "${justAuth.clientId.gitee}")
    private String giteeClienId;
    @Value(value = "${justAuth.clientSecret.gitee}")
    private String giteeClientSecret;
    @Value(value = "${justAuth.redirectUri.gitee}")
    private String giteeRedirectUri;
    @Value(value = "${justAuth.clientId.github}")
    private String githubClienId;
    @Value(value = "${justAuth.clientSecret.github}")
    private String githubClientSecret;
    @Value(value = "${justAuth.redirectUri.github}")
    private String githubRedirectUri;
    @Value(value = "${justAuth.clientId.qq}")
    private String qqClienId;
    @Value(value = "${justAuth.clientSecret.qq}")
    private String qqClientSecret;
    @Value(value = "${justAuth.redirectUri.qq}")
    private String qqRedirectUri;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 获取认证url
     * @param source
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/render")
    public String renderAuth(String source, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest(source);
        String token = AuthStateUtils.createState();
        String authorizeUrl = authRequest.authorize(token);
        Map<String, String> map = new HashMap<>();
        map.put("url", authorizeUrl);
        return ResultVO.ok(map);

    }

    /**
     * 登录
     * @param source
     * @param callback
     * @param request
     * @param httpServletResponse
     * @throws IOException
     */
    @RequestMapping("/callback/{source}")
    public void login(@PathVariable("source") String source, AuthCallback callback, HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException {

        //获取请求的ip地址
        String ip = IpUtils.getRealIp(request);

        AuthRequest authRequest = getAuthRequest(source);
        AuthResponse response = authRequest.login(callback);

        if (!response.ok()){
            // 跳转到500错误页面
            httpServletResponse.sendRedirect("http://localhost:9528/500");
        }

        HashMap<String, Object> data = FastJsonUtil.json2Map(FastJsonUtil.bean2Json(response.getData()));
        if (data == null || data.get("token") == null) {
            // 跳转到500错误页面
            httpServletResponse.sendRedirect("http://localhost:9528/500");
        }

        Boolean exist = false;
        User user = null;
        //判断user是否存在
        if (data.get("uuid") != null && data.get("source") != null) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("id",data.get("source").toString()+data.get("uuid").toString());
            wrapper.eq("source",data.get("source").toString());

            user = userService.getOne(wrapper);

            if (user != null) {
                exist = true;
            } else {
                user = new User();
                user.setId(data.get("source").toString()+data.get("uuid").toString());
                user.setUserName(data.get("nickname").toString());
                // 设置默认密码，默认密码：账号来源
                user.setPassword(passwordEncoder.encode(data.get("source").toString()));
                user.setSource(data.get("source").toString());
                user.setAvatar(data.get("avatar").toString());
            }
        }

        if (!exist){
            boolean save = userService.save(user);
            if (save){
                QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
                roleQueryWrapper.eq("role_name","ROLE_USER");
                Role role = roleService.getOne(roleQueryWrapper);

                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(role.getId());
                boolean b = userRoleService.save(userRole);

                if (!b){
                    // 跳转到500错误页面
                    httpServletResponse.sendRedirect("http://localhost:9528/500");
                }
            }else {
                // 跳转到500错误页面
                httpServletResponse.sendRedirect("http://localhost:9528/500");
            }
        }

        // 创建token
        String token = jwtTokenUtil.generateToken(user);

        // 过期时间
        Integer expiration = jwtTokenUtil.EXPIRATION_TIME;
        Map<String,Object> tokenMap = new HashMap<>();
        tokenMap.put("id",user.getId());
        tokenMap.put("userName",user.getUsername());
        tokenMap.put("ip",ip);
        tokenMap.put("type","web");
        tokenMap.put("token",token);
        tokenMap.put("source",user.getSource());
        tokenMap.put("createTime", DateUtil.getNowTime());
        tokenMap.put("expirationTime",DateUtil.getAddDaySecond(expiration));
        redisUtil.hset("webToken",user.getUsername()+":"+user.getSource(), FastJsonUtil.map2Json(tokenMap),expiration);

        token = jwtTokenUtil.HEAD_Prefix + token;

        httpServletResponse.sendRedirect("http://localhost:9528/" + "?token=" + token);
    }

    /**
     * 鉴权
     * @param source 来源
     * @return
     */
    private AuthRequest getAuthRequest(String source) {
        AuthRequest authRequest = null;
        switch (source) {
            case "github":
                authRequest = new AuthGithubRequest(AuthConfig.builder()
                        .clientId(githubClienId)
                        .clientSecret(githubClientSecret)
                        .redirectUri(githubRedirectUri)
                        .build());
                break;
            case "gitee":
                authRequest = new AuthGiteeRequest(AuthConfig.builder()
                        .clientId(giteeClienId)
                        .clientSecret(giteeClientSecret)
                        .redirectUri(giteeRedirectUri)
                        .build());
                break;
            case "qq":
                authRequest = new AuthQqRequest(AuthConfig.builder()
                        .clientId(qqClienId)
                        .clientSecret(qqClientSecret)
                        .redirectUri(qqRedirectUri)
                        .build());
                break;
            default:
                break;
        }
        if (null == authRequest) {
            throw new AuthException("此登录方式目前不支持");
        }
        return authRequest;
    }

}
