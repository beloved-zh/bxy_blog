//package com.zh.config.web;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @author Beloved
// * @date 2020/8/6 11:22
// */
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//    /**
//     * 页面跨域访问Controller过滤
//     *
//     * @return
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        WebMvcConfigurer.super.addCorsMappings(registry);
//        registry.addMapping("/**")
//                .allowedHeaders("*")
//                .allowedMethods("POST","GET")
//                .allowedOrigins("*");
//    }
//
//}
