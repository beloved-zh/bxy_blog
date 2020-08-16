package com.zh.config.security;

import com.zh.Enums.ResultEnum;
import com.zh.VO.ResultVO;
import com.zh.utils.ResultUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Beloved
 * @date 2020/6/12 17:30
 * @description: 用户未登录时返回给前端的数据
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResultUtil.out(httpServletResponse, ResultVO.ok(ResultEnum.USER_NEED_AUTHORITIES));
    }

}
