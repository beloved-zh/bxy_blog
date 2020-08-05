package com.zh.controller;


import com.zh.VO.ResultVO;
import com.zh.pojo.WebConfig;
import com.zh.service.WebConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 网站配置表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/web")
public class WebConfigController {

    @Autowired
    private WebConfigService webConfigService;

    @PutMapping("/updateConfig")
    public String updateConfig(
            String id,
            String logo,
            String webName,
            String webUrl,
            String summary,
            String author,
            String recordNum,
            String github,
            String gitee,
            String csdn,
            String weixin,
            String qq,
            String email,
            String aliPay,
            String weixinPay){

        WebConfig webConfig = new WebConfig();
        webConfig.setId(id);
        webConfig.setWebName(webName);
        webConfig.setWebUrl(webUrl);
        webConfig.setAuthor(author);
        webConfig.setLogo(logo);
        webConfig.setSummary(summary);
        webConfig.setRecordNum(recordNum);
        webConfig.setGithub(github);
        webConfig.setGitee(gitee);
        webConfig.setCsdn(csdn);
        webConfig.setWeixin(weixin);
        webConfig.setQq(qq);
        webConfig.setEmail(email);
        webConfig.setWeixinPay(weixinPay);
        webConfig.setAliPay(aliPay);


        boolean b = webConfigService.updateById(webConfig);

        if (b){
            return ResultVO.ok("修改成功");
        }
        return ResultVO.failure(400,"修改失败");
    }

    @GetMapping("/getConfig")
    public String getConfig(){

        List<WebConfig> list = webConfigService.list();

        WebConfig config = list.get(0);

        return ResultVO.ok(config);
    }

}

