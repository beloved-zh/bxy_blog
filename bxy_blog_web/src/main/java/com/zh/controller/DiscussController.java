package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zh.VO.ResultVO;
import com.zh.pojo.Discuss;
import com.zh.service.DiscussService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
@RestController
@RequestMapping("/discuss")
public class DiscussController {

    @Autowired
    private DiscussService discussService;


    @GetMapping("/getDiscuss")
    public String getDiscuss(String blogId){

        List<Discuss> list = discussService.getDiscuss(blogId);

        return ResultVO.ok(list);
    }

    @PostMapping("/addDiscussSon")
    public String addDiscussSon(
            String userId,
            String content,
            String blogId,
            String toUserUid,
            String discussId,
            Boolean isFirst){

        Discuss discuss = new Discuss();
        discuss.setBlogId(blogId);
        discuss.setUserId(userId);
        discuss.setContent(content);
        discuss.setToUserUid(toUserUid);
        discuss.setDiscussId(discussId);
        discuss.setIsFirst(isFirst);

        boolean save = discussService.save(discuss);

        if (!save){
            return ResultVO.failure(400,"评论失败");
        }

        return ResultVO.ok("评论成功");
    }


    @PostMapping("/addDiscussFather")
    public String addDiscussFather(
            String userId,
            String content,
            String blogId,
            Boolean isFirst){

        Discuss discuss = new Discuss();
        discuss.setBlogId(blogId);
        discuss.setUserId(userId);
        discuss.setContent(content);
        discuss.setIsFirst(isFirst);

        boolean save = discussService.save(discuss);

        if (!save){
            return ResultVO.failure(400,"评论失败");
        }

        return ResultVO.ok("评论成功");
    }

}

