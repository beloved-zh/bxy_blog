package com.zh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zh.mapper")
public class BxyBlogWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BxyBlogWebApplication.class, args);
    }

}
