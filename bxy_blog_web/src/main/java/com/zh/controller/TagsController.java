package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zh.VO.ResultVO;
import com.zh.pojo.Tags;
import com.zh.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private TagsService tagService;

    @GetMapping("/findAll")
    public String findAll(){
        QueryWrapper<Tags> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        List<Tags> list = tagService.list(wrapper);
        return ResultVO.ok(list);
    }

}

