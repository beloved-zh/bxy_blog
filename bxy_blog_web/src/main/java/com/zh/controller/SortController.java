package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zh.VO.ResultVO;
import com.zh.pojo.Sort;
import com.zh.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
@RestController
@RequestMapping("/sort")
public class SortController {

    @Autowired
    private SortService sortService;

    @GetMapping("/findAll")
    public String findAll(){
        QueryWrapper<Sort> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        List<Sort> list = sortService.list(wrapper);
        return ResultVO.ok(list);
    }

}

