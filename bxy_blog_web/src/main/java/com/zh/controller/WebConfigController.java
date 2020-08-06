package com.zh.controller;


import com.zh.VO.ResultVO;
import com.zh.pojo.WebConfig;
import com.zh.service.WebConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 网站配置表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
@RestController
@RequestMapping("/web")
public class WebConfigController {

    @Autowired
    private WebConfigService webConfigService;

    @GetMapping("/getConfig")
    public String getConfig(){

        List<WebConfig> list = webConfigService.list();

        WebConfig config = list.get(0);

        return ResultVO.ok(config);
    }

}

