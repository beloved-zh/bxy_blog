package com.zh.config.security;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.zh.Enums.ResultEnum;
import com.zh.VO.ResultVO;
import com.zh.utils.RedisUtil;
import com.zh.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Beloved
 * @date 2020/6/12 17:39
 * @description: 用户登录失败时返回给前端的数据
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {

        System.out.println("登录失败：" + e.getMessage());

        String msg = "";

        if (e instanceof LockedException) {
            msg ="账户被锁定";
        } else if (e instanceof CredentialsExpiredException) {
            msg ="密码过期";
        } else if (e instanceof AccountExpiredException) {
            msg ="账户过期";
        } else if (e instanceof DisabledException) {
            msg ="账户被禁用";
        } else if (e instanceof BadCredentialsException) {
            msg ="用户名或者密码输入错误";
        }

        ResultUtil.out(httpServletResponse, ResultVO.ok(ResultEnum.USER_LOGIN_FAILED));
    }

}
