package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.VO.ResultVO;
import com.zh.pojo.Role;
import com.zh.pojo.Sort;
import com.zh.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
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

    @DeleteMapping("/deleteSortList")
    public String deleteSortList(String ids){
        List<String> list = Arrays.asList(ids.split(","));


        boolean b = sortService.removeByIds(list);

        if (b){
            return ResultVO.ok("删除成功");
        }
        return ResultVO.failure(400,"删除失败");
    }

    @DeleteMapping("/deleteSort")
    public String deleteSort(String id){

        boolean b = sortService.removeById(id);

        if (b){
            return ResultVO.ok("删除成功");
        }
        return ResultVO.failure(400,"删除失败");
    }

    @PutMapping("/updateSort")
    public String updateSort(
            String id,
            String sortTitle,
            String content,
            Integer sort){

        Sort byId = sortService.getById(id);
        if (!byId.getSortTitle().equals(sortTitle)){
            QueryWrapper<Sort> wrapper = new QueryWrapper<>();
            wrapper.eq("sort_title",sortTitle);
            Sort one = sortService.getOne(wrapper);
            if (one != null){
                return ResultVO.failure(400,"该类别已存在");
            }
        }
        Sort s = new Sort();
        s.setId(id);
        s.setSortTitle(sortTitle);
        s.setContent(content);
        s.setSort(sort);


        boolean b = sortService.updateById(s);
        if (!b){
            return ResultVO.failure(400,"修改失败");
        }

        return ResultVO.ok("修改成功");
    }

    @PostMapping("/addSort")
    public String addSort(
            String sortTitle,
            String content,
            Integer sort){

        QueryWrapper<Sort> wrapper = new QueryWrapper<>();
        wrapper.eq("sort_title",sortTitle);

        Sort one = sortService.getOne(wrapper);
        if (one != null){
            return ResultVO.failure(400,"该类别已存在");
        }

        Sort s = new Sort();
        s.setSortTitle(sortTitle);
        s.setContent(content);
        s.setSort(sort);

        boolean b = sortService.save(s);

        if (!b){
            return ResultVO.failure(400,"添加失败");
        }

        return ResultVO.ok("添加成功");
    }

    @GetMapping("/getSort")
    public String getSort(
            String keyword,
            Integer currentPage,
            Integer pageSize){

        QueryWrapper<Sort> wrapper = new QueryWrapper<>();

        wrapper.like("sort_title",keyword);
        wrapper.orderByAsc("sort");

        Page<Sort> page = new Page<>(currentPage,pageSize);

        sortService.page(page, wrapper);

        return ResultVO.ok(page);
    }

}

