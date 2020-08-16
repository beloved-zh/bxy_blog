package com.zh.config.security;
import com.zh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author Beloved
 * @date 2020/6/12 13:16
 */
@SuppressWarnings("ALL")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    // 未登陆时处理器
    @Autowired
    MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    // 登录成功
    @Autowired
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    // 登录失败
    @Autowired
    MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    // 注销成功
    @Autowired
    MyLogoutSuccessHandler myLogoutSuccessHandler;

    // 无权访问 403
    @Autowired
    MyAccessDeniedHandler myAccessDeniedHandler;

    // jwt拦截器
    @Autowired
    MyOncePerRequestFilter myOncePerRequestFilter;

    // 加密方式
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //认证用户的来源（内存或数据库）
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 去掉 CSRF
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 使用 JWT，关闭token
                .and()

                .httpBasic().authenticationEntryPoint(myAuthenticationEntryPoint)

                .and()
                .authorizeRequests()//定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers("/oauth/**","/blog/**","/links/**","/web/**","/discuss/getDiscuss")
                .permitAll()

                .anyRequest()//任何请求,登录后可以访问
                .access("@myRbacService.hasPermission(request,authentication)") // RBAC 动态 url 认证

                .and()
                .logout()//默认注销行为为logout
                .logoutUrl("/auth/logout")
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .permitAll();


        http.formLogin().loginProcessingUrl("/auth/login")
            .successHandler(myAuthenticationSuccessHandler)
            .failureHandler(myAuthenticationFailureHandler);
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler); // 无权访问 JSON 格式的数据
        http.addFilterBefore(myOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class); // JWT Filter

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:9528","http://192.168.137.1:9528","http://3p0046245d.wicp.vip","http://localhost:9528"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        configuration.applyPermitDefaultValues();

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }

}

