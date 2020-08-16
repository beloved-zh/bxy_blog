package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zh.VO.ResultVO;
import com.zh.pojo.Blog;
import com.zh.pojo.Links;
import com.zh.pojo.Tags;
import com.zh.service.BlogService;
import com.zh.service.TagsService;
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

    @Autowired
    private TagsService tagsService;

    @GetMapping("/getBlogById")
    public String getBlogById(String id){
        Blog blog = blogService.getBlogById(id);

        List<Tags> tags = tagsService.findByBlogId(blog.getId());
        blog.setTags(tags);

        return ResultVO.ok(blog);
    }

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

    @GetMapping("/getBlogByTop")
    public String getBlogByTop(){

        List<Blog> list = blogService.getBlogByTop();

        return ResultVO.ok(list);
    }
}

