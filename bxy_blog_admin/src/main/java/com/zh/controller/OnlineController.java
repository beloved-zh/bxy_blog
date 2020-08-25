package com.zh.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zh.VO.ResultVO;
import com.zh.pojo.User;
import com.zh.service.UserService;
import com.zh.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Beloved
 * @date 2020/8/25 12:28
 */
@RestController
@RequestMapping("/online")
public class OnlineController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    @GetMapping("/getOnline")
    public String getOnline(Integer currentPage, Integer pageSize){

        Set<Object> online = redisUtil.sGet("Online");

        IPage<User> page = userService.getUserByIds(online,currentPage,pageSize);

        return ResultVO.ok(page);
    }

    @DeleteMapping("/deleteOnline")
    public String deleteOnline(String id,String username,String source){

        long remove = redisUtil.setRemove("Online", id);

        redisUtil.hdel("webToken",username+":"+source);

        if (remove > 0) {
            return ResultVO.ok();
        }
        return ResultVO.failure();
    }
}
