package com.zh;

import com.zh.pojo.User;
import com.zh.service.BlogService;
import com.zh.service.UserService;
import com.zh.utils.DateUtil;
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

        System.out.println(DateUtil.getMonthStartTimeStr());
        System.out.println(DateUtil.getMonthBetweenDates("2020-07-24 13:31:28", DateUtil.getMonthStartTimeStr()));
    }

}
