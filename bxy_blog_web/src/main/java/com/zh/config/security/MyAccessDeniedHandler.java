package com.zh.config.security;

import com.zh.Enums.ResultEnum;
import com.zh.VO.ResultVO;
import com.zh.utils.ResultUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Beloved
 * @date 2020/6/12 17:44
 * @description: 无权访问
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) {
        ResultUtil.out(httpServletResponse, ResultVO.ok(ResultEnum.USER_NO_ACCESS));
    }
}
