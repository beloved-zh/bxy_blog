package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zh.VO.ResultVO;
import com.zh.pojo.Blog;
import com.zh.service.*;
import com.zh.utils.DateUtil;
import com.zh.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private SortService sortService;

    @Autowired
    private TagsService tagsService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/init")
    public String init(){

        long online = redisUtil.sGetSetSize("Online");
        int userCount = userService.count();
        int discussCount = discussService.count();
        int blogCount = blogService.count();

        Map<String, Object> map = new HashMap<>();

        map.put("online", online);
        map.put("userCount", userCount);
        map.put("discussCount", discussCount);
        map.put("blogCount", blogCount);

        return ResultVO.ok(map);
    }

    @GetMapping("/getBlogCountByBlogSort")
    public String getBlogCountByBlogSort(){

        List<Map<String, Object>> list = sortService.getBlogCountByBlogSort();

        return ResultVO.ok(list);
    }

    @GetMapping("/getBlogCountByTag")
    public String getBlogCountByTag(){

        List<Map<String, Object>> list = tagsService.getBlogCountByTag();

        return ResultVO.ok(list);
    }

    @GetMapping("/getBlogCountByLevel")
    public String getBlogCountByLevel(){

        List<Map<String, Object>> list = blogService.getBlogCountByLevel();

        return ResultVO.ok(list);
    }

    @GetMapping("/getBlogContributeCount")
    public String getBlogContributeCount(){

        Map<String, Object> map = blogService.getBlogContributeCount();
        return ResultVO.ok(map);
    }
}

