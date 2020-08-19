package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zh.VO.ResultVO;
import com.zh.pojo.Fabulous;
import com.zh.service.FabulousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 点赞表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
@RestController
@RequestMapping("/fabulous")
public class FabulousController {

    @Autowired
    private FabulousService fabulousService;

    @GetMapping("/getFabulousByUser")
    public String getFabulousByUser(String userId){

        List<Fabulous> list = fabulousService.getFabulousByUser(userId);

        return ResultVO.ok(list);
    }

    @DeleteMapping("/deleteFabulous")
    public String deleteFabulous(String userId,String blogId,String discussId,Boolean type){
        QueryWrapper<Fabulous> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        if (!StringUtils.isEmpty(blogId)){
            wrapper.eq("blog_id",blogId);
        }
        if (!StringUtils.isEmpty(discussId)){
            wrapper.eq("discuss_id",discussId);
        }
        wrapper.eq("0",type ? 1 : 0);

        boolean remove = fabulousService.remove(wrapper);

        if (!remove){
            return ResultVO.failure(400,"操作失败");
        }

        return ResultVO.ok("取消点赞");

    }

    @GetMapping("/judgeClickZan")
    public String judgeClickZan(String userId,String blogId){

        QueryWrapper<Fabulous> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("blog_id",blogId);

        List<Fabulous> list = fabulousService.list(wrapper);

        if (list.size() == 0){
            return ResultVO.ok(false);
        }else {
            return ResultVO.ok(true);
        }
    }

    @PostMapping("/addFabulous")
    public String addFabulous(Fabulous fabulous){

        boolean save = fabulousService.save(fabulous);

        if (!save){
            return ResultVO.failure(400,"点赞失败");
        }

        return ResultVO.ok("点赞成功");
    }

}

