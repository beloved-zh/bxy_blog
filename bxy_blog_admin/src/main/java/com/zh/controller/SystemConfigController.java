package com.zh.controller;


import com.zh.VO.ResultVO;
import com.zh.utils.FastJsonUtil;
import com.zh.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.lang.model.type.ErrorType;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 系统配置表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/system")
public class SystemConfigController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/getQiniuConfig")
    public String getQiniuConfig(){

        Object qiniu = redisUtil.hget("SystemConfig", "qiniu");

        HashMap<String, Object> map = FastJsonUtil.json2Map(qiniu.toString());

        return ResultVO.ok(map);
    }

    @PutMapping("/updateQiniuConfig")
    public String updateQiniuConfig(
            String prefix,
            String accessKey,
            String secretKey,
            String bucket,
            String zone){

        Map<String, Object> map = new HashMap<>();
        map.put("prefix",prefix);
        map.put("accessKey",accessKey);
        map.put("secretKey",secretKey);
        map.put("bucket",bucket);
        map.put("zone",zone);

        boolean b = redisUtil.hset("SystemConfig","qiniu", FastJsonUtil.map2Json(map));

        if (b){
            return ResultVO.ok();
        }else {
            return ResultVO.failure();
        }
    }

    @GetMapping("/getOauthConfig")
    public String getOauthConfig(){

        Object oauth = redisUtil.hget("SystemConfig", "oauth");

        HashMap<String, Object> map = FastJsonUtil.json2Map(oauth.toString());

        return ResultVO.ok(map);
    }

    @GetMapping("/getJwtConfig")
    public String getJwtConfig(){

        Object oauth = redisUtil.hget("SystemConfig", "jwt");

        HashMap<String, Object> map = FastJsonUtil.json2Map(oauth.toString());

        return ResultVO.ok(map);
    }

    @PutMapping("/updateJwtConfig")
    public String updateJwtConfig(
            String secretKey,
            String expirationTime,
            String head,
            String headPrefix){

        Map<String, Object> map = new HashMap<>();
        map.put("secretKey",secretKey);
        map.put("expirationTime",expirationTime);
        map.put("head",head);
        map.put("headPrefix",headPrefix);

        boolean b = redisUtil.hset("SystemConfig","jwt", FastJsonUtil.map2Json(map));

        if (b){
            return ResultVO.ok();
        }else {
            return ResultVO.failure();
        }
    }

    @PutMapping("/updateOauthConfig")
    public String updateOauthConfig(
            String GithubClientId,
            String GithubClientSecret,
            String GithubRedirectUri,
            String GiteeClientId,
            String GiteeClientSecret,
            String GiteeRedirectUri,
            String QQClientId,
            String QQClientSecret,
            String QQRedirectUri,
            String WeixinClientId,
            String WeixinClientSecret,
            String WeixinRedirectUri
            ){


        Map<String, Object> map = new HashMap<>();
        map.put("GithubClientId",GithubClientId);
        map.put("GithubClientSecret",GithubClientSecret);
        map.put("GithubRedirectUri",GithubRedirectUri);
        map.put("GiteeClientId",GiteeClientId);
        map.put("GiteeClientSecret",GiteeClientSecret);
        map.put("GiteeRedirectUri",GiteeRedirectUri);
        map.put("QQClientId",QQClientId);
        map.put("QQClientSecret",QQClientSecret);
        map.put("QQRedirectUri",QQRedirectUri);
        map.put("WeixinClientId",WeixinClientId);
        map.put("WeixinClientSecret",WeixinClientSecret);
        map.put("WeixinRedirectUri",WeixinRedirectUri);

        boolean b = redisUtil.hset("SystemConfig","oauth", FastJsonUtil.map2Json(map));

        if (b){
            return ResultVO.ok();
        }else {
            return ResultVO.failure();
        }
    }

}

