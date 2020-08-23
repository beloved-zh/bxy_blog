package com.zh.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zh.VO.ResultVO;
import com.zh.pojo.Feedback;
import com.zh.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/getFeedbackByUser")
    public String getFeedbackByUser(String userId) {

        QueryWrapper<Feedback> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);

        List<Feedback> list = feedbackService.list(wrapper);

        return ResultVO.ok(list);
    }

    @PostMapping("/addFeedback")
    public String addFeedback(
            String title,
            String content,
            String userId){

        Feedback feedback = new Feedback();
        feedback.setTitle(title);
        feedback.setContent(content);
        feedback.setUserId(userId);
        feedback.setState(0);

        boolean save = feedbackService.save(feedback);

        if (save){
            return ResultVO.ok();
        }else {
            return ResultVO.failure();
        }
    }

}
