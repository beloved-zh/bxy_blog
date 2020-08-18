package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.VO.ResultVO;
import com.zh.pojo.Links;
import com.zh.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/addLink")
    public String addLink(
            String linkName,
            String linkUrl,
            String summary,
            String userId){

        int count = linksService.count();

        Links link = new Links();
        link.setLinkName(linkName);
        link.setLinkUrl(linkUrl);
        link.setSummary(summary);
        link.setUserId(userId);
        link.setSort(count);
        link.setLinkStatus(false);

        boolean save = linksService.save(link);

        if (!save){
            return ResultVO.failure(400,"申请失败");
        }

        return ResultVO.ok("申请成功");
    }

    @GetMapping("/getLinks")
    public String getLinks(){

        QueryWrapper<Links> wrapper = new QueryWrapper<>();

        wrapper.orderByAsc("sort");

        wrapper.eq("link_status",1);

        List<Links> list = linksService.list(wrapper);

        return ResultVO.ok(list);

    }

    @GetMapping("/getLinkByUserAndStatus")
    public String getLinkByUserAndStatus(String userId,Integer linkStatus){

        QueryWrapper<Links> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("link_status",linkStatus);

        List<Links> list = linksService.list(wrapper);

        return ResultVO.ok(list);
    }

}

