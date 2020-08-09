package com.zh.controller;

import com.zh.VO.ResultVO;
import com.zh.utils.FastJsonUtil;
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
import org.springframework.beans.factory.annotation.Value;
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

    @RequestMapping("/render")
    public String renderAuth(String source, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest(source);
        String token = AuthStateUtils.createState();
        String authorizeUrl = authRequest.authorize(token);
        Map<String, String> map = new HashMap<>();
        map.put("url", authorizeUrl);
        return ResultVO.ok(map);
    }

    @RequestMapping("/callback/{source}")
    public void login(@PathVariable("source") String source, AuthCallback callback, HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException {

        AuthRequest authRequest = getAuthRequest(source);
        AuthResponse response = authRequest.login(callback);

        Object data = response.getData();

        System.out.println("用户信息");
        System.out.println(FastJsonUtil.bean2Json(data));

        httpServletResponse.sendRedirect("http://localhost:9528/" + "?token=" + authRequest);
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
