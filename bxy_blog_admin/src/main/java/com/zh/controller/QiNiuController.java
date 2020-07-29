package com.zh.controller;

import com.qiniu.cdn.CdnResult;
import com.qiniu.storage.model.FileInfo;
import com.zh.VO.ResultVO;
import com.zh.utils.QiNiuUtil;
import com.zh.utils.UUIDUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Beloved
 * @date 2020/7/20 14:58
 * 文件上传处理器
 */
@RestController
@RequestMapping("/qiniu")
public class QiNiuController {

    @DeleteMapping("/deleteList")
    public String deleteList(String bucket,String keys,String urls){

        String[] keyList = keys.split(",");
        String[] urlList = urls.split(",");

        boolean delete = QiNiuUtil.deleteList(bucket, keyList);

        if (!delete){
            return ResultVO.failure(400,"删除失败");
        }

        CdnResult.RefreshResult refresh = QiNiuUtil.refresh(urlList);
        if (refresh.code != 200){
            return ResultVO.ok("删除成功,CDN缓存刷新失败",refresh);
        }

        return ResultVO.ok("删除成功",refresh);
    }

    @DeleteMapping("/delete")
    public String delete(String bucket,String fileName,String url){

        boolean delete = QiNiuUtil.delete(bucket, fileName);

        if (!delete){
            return ResultVO.failure(400,"删除失败");
        }

        String[] urls = {url};
        CdnResult.RefreshResult refresh = QiNiuUtil.refresh(urls);
        if (refresh.code != 200){
            return ResultVO.ok("删除成功,CDN缓存刷新失败",refresh);
        }

        return ResultVO.ok("删除成功",refresh);
    }

    @GetMapping("getBaseURL")
    public String getBaseURL(String bucket){

        String url = QiNiuUtil.getBaseURL(bucket);

        url = "http://" + url;

        if (StringUtils.isEmpty(url)){
            return ResultVO.failure(400,"此存储空间没有绑定域名");
        }

        return ResultVO.ok(url);
    }

    @GetMapping("/getList")
    public String getList(String bucket,String keyword){

        FileInfo[] list = QiNiuUtil.getBucketList(bucket, keyword);

        return ResultVO.ok(list);
    }

    @GetMapping("/getBuckets")
    public String getBuckets(){

        String[] buckets = QiNiuUtil.getBuckets();

        if (buckets == null){
            return ResultVO.failure(400,"此用户没有存储空间");
        }
        return ResultVO.ok(buckets);
    }

    @PostMapping("/uploadImage")
    public String uploadImage(MultipartFile file){

        try {
            String fileName = UUIDUtil.getUUID();
            String suffix = file.getOriginalFilename();
            suffix = suffix.substring(suffix.lastIndexOf("."));
            String url = QiNiuUtil.upload(file.getInputStream(), fileName+suffix);
            if (StringUtils.isEmpty(url)){
                return ResultVO.failure(400,"文件上传失败");
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("fileName",fileName);
            map.put("fileUrl",url);
            return ResultVO.ok(map);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVO.failure(400,"文件上传失败");
        }

    }

    @PostMapping("/uploade")
    public String uploade(String bucket,MultipartFile file){
        try {
            String fileName = UUIDUtil.getUUID();
            String suffix = file.getOriginalFilename();
            suffix = suffix.substring(suffix.lastIndexOf("."));
            String key = QiNiuUtil.upload(bucket, file.getInputStream(), fileName + suffix);
            if (StringUtils.isEmpty(key)){
                return ResultVO.failure(400,"文件上传失败");
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("fileName",fileName);
            map.put("fileKey",key);
            return ResultVO.ok(map);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVO.failure(400,"文件上传失败");
        }
    }

}
