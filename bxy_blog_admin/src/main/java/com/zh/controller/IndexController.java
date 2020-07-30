package com.zh.controller;


import com.zh.VO.ResultVO;
import com.zh.service.BlogService;
import com.zh.service.DiscussService;
import com.zh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 主页控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private DiscussService discussService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/init")
    public String init(){

        int visitCount = 8888;
        int userCount = userService.count();
        int discussCount = discussService.count();
        int blogCount = blogService.count();

        Map<String, Integer> map = new HashMap<>();

        map.put("visitCount", visitCount);
        map.put("userCount", userCount);
        map.put("discussCount", discussCount);
        map.put("blogCount", blogCount);

        return ResultVO.ok(map);
    }

}

