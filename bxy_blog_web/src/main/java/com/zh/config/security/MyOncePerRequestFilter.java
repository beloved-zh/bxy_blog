package com.zh.config.security;

import com.zh.service.UserService;
import com.zh.utils.FastJsonUtil;
import com.zh.utils.JwtTokenUtil;
import com.zh.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * jwt拦截器
 * @description: 确保在一次请求只通过一次filter，而不需要重复执行
 */
@Component
public class MyOncePerRequestFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        // 从请求头获取token
        String headerToken = request.getHeader(jwtTokenUtil.HEAD);
        String username = null;
        String token = null;

        // 判断token是否是空并且是否以指定前缀开始
        if (headerToken != null && headerToken.startsWith(jwtTokenUtil.HEAD_Prefix)) {

            // 去除前缀
            token = headerToken.substring(jwtTokenUtil.HEAD_Prefix.length());

            // 从token中获取用户名
            username = jwtTokenUtil.getUsernameFromToken(token);


            //判断用户不为空，且SecurityContextHolder授权信息还是空的
            if (!StringUtils.isEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {

                // 通过用户信息得到UserDetails
                UserDetails userDetails = userService.loadUserByUsername(username);

                try {
                    // 验证令牌是否过期
                    if (redisUtil.hHasKey("webToken", username)) {
                        String object = (String) redisUtil.hget("webToken", username);
                        HashMap<String, Object> map = FastJsonUtil.json2Map(object);
                        // 判断是否有效
                        if (jwtTokenUtil.validateToken((String) map.get("token"),userDetails)) {
                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        chain.doFilter(request, response);
    }
}
