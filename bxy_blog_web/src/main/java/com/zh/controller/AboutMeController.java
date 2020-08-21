package com.zh.controller;

import com.zh.VO.ResultVO;
import com.zh.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Beloved
 * @date 2020/8/21 21:50
 */
@RestController
@RequestMapping("/aboutMe")
public class AboutMeController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/getAboutMe")
    public String getAboutMe(){

        Map<Object, Object> aboutMe = redisUtil.hmget("AboutMe");

        return ResultVO.ok(aboutMe);
    }

}
