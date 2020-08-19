package com.zh.config.security;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.zh.Enums.ResultEnum;
import com.zh.VO.ResultVO;
import com.zh.utils.JwtTokenUtil;
import com.zh.utils.RedisUtil;
import com.zh.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Beloved
 * @date 2020/6/12 17:45
 * @description: 登出成功
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        String headerToken = httpServletRequest.getHeader(jwtTokenUtil.HEAD);

        if (headerToken != null && headerToken.startsWith(jwtTokenUtil.HEAD_Prefix)) {

            String token = headerToken.substring(jwtTokenUtil.HEAD_Prefix.length());

            //删除token
            redisUtil.del(token);
        }

        ResultUtil.out(httpServletResponse, ResultVO.ok(ResultEnum.USER_LOGOUT_SUCCESS));
    }

}
