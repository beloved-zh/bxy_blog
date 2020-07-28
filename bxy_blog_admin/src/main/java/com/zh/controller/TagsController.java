package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.VO.ResultVO;
import com.zh.pojo.Tags;
import com.zh.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
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

    @DeleteMapping("/deleteTagList")
    public String deleteTagList(String ids){
        List<String> list = Arrays.asList(ids.split(","));


        boolean b = tagService.removeByIds(list);

        if (b){
            return ResultVO.ok("删除成功");
        }
        return ResultVO.failure(400,"删除失败");
    }

    @DeleteMapping("/deleteTag")
    public String deleteTag(String id){

        boolean b = tagService.removeById(id);

        if (b){
            return ResultVO.ok("删除成功");
        }
        return ResultVO.failure(400,"删除失败");
    }

    @PutMapping("/updateTag")
    public String updateTag(
            String id,
            String tagTitle,
            Integer sort){

        Tags t = tagService.getById(id);
        if (!t.getTagTitle().equals(tagTitle)){
            QueryWrapper<Tags> wrapper = new QueryWrapper<>();
            wrapper.eq("tag_title",tagTitle);
            Tags one = tagService.getOne(wrapper);
            if (one != null){
                return ResultVO.failure(400,"该标签已存在");
            }
        }
        Tags tag = new Tags();
        tag.setId(id);
        tag.setTagTitle(tagTitle);
        tag.setSort(sort);


        boolean b = tagService.updateById(tag);
        if (!b){
            return ResultVO.failure(400,"修改失败");
        }

        return ResultVO.ok("修改成功");
    }

    @PostMapping("/addTag")
    public String addTag(
            String tagTitle,
            Integer sort){

        QueryWrapper<Tags> wrapper = new QueryWrapper<>();
        wrapper.eq("tag_title",tagTitle);

        Tags one = tagService.getOne(wrapper);
        if (one != null){
            return ResultVO.failure(400,"该标签已存在");
        }

        Tags tag = new Tags();
        tag.setTagTitle(tagTitle);
        tag.setSort(sort);

        boolean b = tagService.save(tag);

        if (!b){
            return ResultVO.failure(400,"添加失败");
        }

        return ResultVO.ok("添加成功");
    }

    @GetMapping("/getTags")
    public String getTags(
            String keyword,
            Integer currentPage,
            Integer pageSize){

        QueryWrapper<Tags> wrapper = new QueryWrapper<>();

        wrapper.like("tag_title",keyword);
        wrapper.orderByAsc("sort");

        Page<Tags> page = new Page<>(currentPage,pageSize);

        tagService.page(page, wrapper);

        return ResultVO.ok(page);

    }

}

