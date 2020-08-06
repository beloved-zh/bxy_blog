package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zh.VO.ResultVO;
import com.zh.pojo.Blog;
import com.zh.pojo.Links;
import com.zh.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 博客表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/getBlogByLevel")
    public String getBlogByLevel(Integer level){

        List<Blog> list = blogService.getBlogByLevel(level);

        return ResultVO.ok(list);
    }

    @GetMapping("/getBlogByLevelAndPage")
    public String getBlogByLevelAndPage(Integer level, Integer currentPage, Integer pageSize){

        IPage<Blog> page = blogService.getBlogByLevelAndPage(level, currentPage, pageSize);

        return ResultVO.ok(page);
    }
}
