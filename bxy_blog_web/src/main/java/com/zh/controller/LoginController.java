package com.zh.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zh.Enums.ResultEnum;
import com.zh.VO.ResultVO;
import com.zh.pojo.User;
import com.zh.service.MenuService;
import com.zh.service.RoleService;
import com.zh.service.UserService;
import com.zh.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  登录授权操作前端控制器
 * </p>
 * @author Beloved
 * @date 2020/6/23 13:15
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(HttpServletRequest request, String username, String password){

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ResultVO.failure(400,"账号或密码不能为空");
        }

        //获取请求的ip地址
        String ip = IpUtils.getRealIp(request);

        Object blackList = redisUtil.hget("blackList", ip);
        Integer limitCount = 0;
        if (blackList != null){
            limitCount = (Integer) blackList;
            if (limitCount >= 5) {
                return ResultVO.failure(400,"密码输错次数过多,已被锁定30分钟");
            }
        }


        Boolean isEmail = CheckUtils.checkEmail(username);
        Boolean isMobile = CheckUtils.checkMobileNumber(username);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (isEmail) {
            queryWrapper.eq("email", username);
        } else if (isMobile) {
            queryWrapper.eq("mobile", username);
        } else {
            queryWrapper.eq("user_name", username);
        }
        queryWrapper.eq("source", "BXY");

        User user = userService.getOne(queryWrapper);

        if (user == null){
            redisUtil.hset("blackList",ip,limitCount+1,60*30);
            return ResultVO.failure(400,"账号或密码错误，错误"+(4-limitCount)+"次后，账户将被锁定30分钟");
        }

        boolean isPassword = passwordEncoder.matches(password, user.getPassword());
        if (!isPassword) {
            redisUtil.hset("blackList",ip,limitCount+1,60*30);
            //密码错误，返回提示
            return  ResultVO.failure(400,"账号或密码错误，错误"+(4-limitCount)+"次后，账户将被锁定30分钟");
        }

        userService.updateLogin(user.getId(),user.getLoginCount()+1,DateUtil.getNowTime(),ip);

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
        tokenMap.put("source","BXY");
        tokenMap.put("createTime", DateUtil.getNowTime());
        tokenMap.put("expirationTime",DateUtil.getAddDaySecond(expiration));
        redisUtil.hset("webToken",user.getUsername()+":"+user.getSource(), FastJsonUtil.map2Json(tokenMap),expiration);

        token = jwtTokenUtil.HEAD_Prefix + token;
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);

        return ResultVO.ok(ResultEnum.USER_LOGIN_SUCCESS,token);
    }


    @GetMapping("/info")
    public String getInfo(HttpServletRequest request){

        String token = request.getHeader(jwtTokenUtil.HEAD);

        // 去除前缀
        token = token.substring(jwtTokenUtil.HEAD_Prefix.length());

        // 从token中获取用户名和账号来源
        String username = jwtTokenUtil.getUsernameFromToken(token);
        String source = jwtTokenUtil.getTokenByKey(token,"source");

        User user = userService.getUserByUserNameAndSource(username,source);

        HashMap<String, Object> map = new HashMap<>();
        map.put("id",user.getId());
        map.put("username", user.getUsername());
        map.put("avatar", user.getAvatar());
        map.put("source", user.getSource());
        map.put("email", user.getEmail());
        map.put("mobile", user.getMobile());

        return ResultVO.ok(ResultEnum.SUCCESS,map);
    }


}
