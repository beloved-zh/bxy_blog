package com.zh.config.security;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.zh.Enums.ResultEnum;
import com.zh.VO.ResultVO;
import com.zh.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Beloved
 * @date 2020/6/12 17:42
 * @description: 用户登录成功时返回给前端的数据
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        //取得账号信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 创建token
        String token = jwtTokenUtil.generateToken(userDetails);

        //获取请求的ip地址
        String ip = IpUtils.getRealIp(httpServletRequest);
        // 过期时间
        Integer expiration = jwtTokenUtil.EXPIRATION_TIME;

        redisUtil.hset(token,"userName",userDetails.getUsername(),expiration);
        redisUtil.hset(token,"ip",ip,expiration);
        redisUtil.hset(token,"type","admin",expiration);
        redisUtil.hset(token,"token",token,expiration);
        redisUtil.hset(token,"source","BXY",expiration);
        redisUtil.hset(token,"createTime",DateUtil.getNowTime(),expiration);
        redisUtil.hset(token,"expirationTime",DateUtil.getAddDaySecond(expiration),expiration);

        token = jwtTokenUtil.HEAD_Prefix + token;
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);

        ResultUtil.out(httpServletResponse, ResultVO.ok(ResultEnum.USER_LOGIN_SUCCESS,token));
    }
}
