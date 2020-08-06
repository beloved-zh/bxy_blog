package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.VO.ResultVO;
import com.zh.pojo.Links;
import com.zh.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 友情链接表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
@RestController
@RequestMapping("/links")
public class LinksController {

    @Autowired
    private LinksService linksService;

    @GetMapping("/getLinks")
    public String getLinks(){

        QueryWrapper<Links> wrapper = new QueryWrapper<>();

        wrapper.orderByAsc("sort");

        wrapper.eq("link_status",1);

        List<Links> list = linksService.list(wrapper);

        return ResultVO.ok(list);

    }

}

