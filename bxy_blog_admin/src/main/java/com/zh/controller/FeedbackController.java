package com.zh.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.VO.ResultVO;
import com.zh.pojo.Feedback;
import com.zh.pojo.Links;
import com.zh.pojo.User;
import com.zh.service.FeedbackService;
import com.zh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 反馈表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-08-23
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserService userService;

    @PutMapping("/updateFeedback")
    public String updateFeedback(String id,Integer state,String reply){
        QueryWrapper<Feedback> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);

        Feedback feedback = new Feedback();
        feedback.setState(state);
        feedback.setReply(reply);

        boolean update = feedbackService.update(feedback,wrapper);

        if (update){
            return ResultVO.ok();
        }
        return ResultVO.failure();
    }

    @DeleteMapping("/deleteFeedback")
    public String deleteFeedback(String id){

        boolean b = feedbackService.removeById(id);

        if (b){
            return ResultVO.ok("删除成功");
        }
        return ResultVO.failure(400,"删除失败");
    }

    @GetMapping("/getFeedback")
    public String getFeedback(
            String keyword,
            Integer state,
            Integer currentPage,
            Integer pageSize){

        QueryWrapper<Feedback> wrapper = new QueryWrapper<>();
        wrapper.like("title",keyword);
        wrapper.orderByDesc("create_time");
        if (!StringUtils.isEmpty(state)){
            wrapper.eq("state",state);
        }

        Page<Feedback> page = new Page<>(currentPage,pageSize);

        feedbackService.page(page, wrapper);

        for (Feedback feedback : page.getRecords()) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("id","user_name","avatar");
            queryWrapper.eq("id",feedback.getUserId());
            User user = userService.getOne(queryWrapper);

            feedback.setUser(user);
        }

        return ResultVO.ok(page);
    }

}
