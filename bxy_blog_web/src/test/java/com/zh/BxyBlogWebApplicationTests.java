package com.zh;

import com.zh.pojo.User;
import com.zh.service.BlogService;
import com.zh.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BxyBlogWebApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Test
    void contextLoads() {
//        System.out.println(blogService.getBlogByOneLevel());
    }

}
