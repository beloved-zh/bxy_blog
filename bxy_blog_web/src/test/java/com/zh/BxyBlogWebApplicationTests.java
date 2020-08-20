package com.zh;

import com.zh.pojo.User;
import com.zh.service.BlogService;
import com.zh.service.UserService;
import com.zh.utils.DateUtil;
import com.zh.utils.JwtTokenUtil;
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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Test
    void contextLoads() {

        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInNvdXJjZSI6IkJYWSIsImV4cCI6MTU5ODAxOTk3MiwiaWF0IjoxNTk3OTMzNTcyNDY5fQ.2HYi9eLQT1JRyRtMMIUN-mWQt55vcHMw2JHn9SDSwjvmXA9UOoPhjRGPN949y6FsajXrByvuHMBi-DTJhRX_LQ";

        String source = jwtTokenUtil.getTokenByKey(token,"source");

        System.out.println(source);

    }

}
