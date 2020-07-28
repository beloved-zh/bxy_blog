package com.zh.config.security;


import com.zh.pojo.RequestApi;
import com.zh.service.RequestApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Beloved
 * @date 2020/6/14 14:02
 * 动态加载权限
 */
@SuppressWarnings("ALL")
@Component("myRbacService")
public class MyRbacService {

    @Autowired
    private RequestApiService requestApiService;

    // 判断某用户是否具有该资源访问权限
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        // 拿到验证主体
        Object principal = authentication.getPrincipal();

        // 判断是否是UserDetails
        if(principal instanceof UserDetails) {

            UserDetails userDetails = (UserDetails) principal;
            //得到当前的账号
            String username = userDetails.getUsername();

            //通过账号获取可访问的url地址
            List<RequestApi> urls = requestApiService.getRequestApiByUserName(username);

            //当前访问路径
            String requestURI = request.getRequestURI();
            System.out.println("当前访问路径："+requestURI);
            //提交类型
            String urlMethod = request.getMethod();
            //如果存入类型还需判断
            System.out.println("当前访问类型："+urlMethod);
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            // 判断是否包含请求的url 包含返回true
            return urls.stream().anyMatch(item -> {
                boolean hashAntPath = antPathMatcher.match(item.getUrl(), requestURI);
                if (hashAntPath){
                    String dbMethod = item.getMethod();
                    if (urlMethod.equals(dbMethod) || dbMethod.equals("ALL")){
                        return true;
                    }
                    return false;
                }
                return false;
            });
        }
        return false;
    }

}
