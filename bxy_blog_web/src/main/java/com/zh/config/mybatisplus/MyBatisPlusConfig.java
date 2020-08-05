package com.zh.config.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Beloved
 * @date 2020/7/2 12:16
 */
//扫描mapper
@MapperScan("com.zh.mapper")
@EnableTransactionManagement
@Configuration  //配置类
public class MyBatisPlusConfig {


    //分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
