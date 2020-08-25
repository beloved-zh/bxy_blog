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
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 开启iframe引入
        http.headers().frameOptions().disable();
        // CRSF禁用，因为不使用session
        http.cors().and().csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 未登录
                .httpBasic().authenticationEntryPoint(myAuthenticationEntryPoint)
                .and()
                // 过滤请求 对于一下请求 允许匿名访问
                .authorizeRequests()//定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers(
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/v2/**",
                        "/swagger-resources/**",
                        "/oauth/**",
                        "/blog/**",
                        "/tags/**",
                        "/links/**",
                        "/sort/**",
                        "/web/**",
                        "/aboutMe/**",
                        "/discuss/getDiscuss",
                        "/druid/**"
                ).permitAll()
                .antMatchers("/auth/login").anonymous()
                .antMatchers(
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()

                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest()//任何请求,登录后可以访问
                .access("@myRbacService.hasPermission(request,authentication)") // RBAC 动态 url 认证

                .and()
                .logout()//默认注销行为为logout
                .logoutUrl("/auth/logout")
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .permitAll();


//        http.formLogin().loginProcessingUrl("/auth/login")
//            .successHandler(myAuthenticationSuccessHandler)
//            .failureHandler(myAuthenticationFailureHandler);
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

