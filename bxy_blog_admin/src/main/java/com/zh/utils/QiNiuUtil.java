package com.zh.utils;

import com.qiniu.cdn.CdnManager;
import com.qiniu.cdn.CdnResult;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.*;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author Beloved
 * @date 2020/7/9 14:52
 * 七牛上传下载工具类
 */
public class QiNiuUtil {

    private static String accessKey = "ZM1tafYFWffVVe-VeWKXuU6kAa5hFZNEZzcEsk-Q";
    private static String secretKey = "BHvxK6XAKlZ4M5x10DtWJHMdxprvAiAiQYqjMdkB";
    private static String bucket = "bxyzh";
    private static String prefix = "http://bxyimage.beloved.ink";

    /**
     * 通过文件来传递数据
     * @param bucketNm
     * @param file
     * @return
     */
    public static boolean upload(String bucketNm, File file, String key) {
        try {

            String token = getToken(bucketNm);
            Response response = uploadManager().put(file,key, token);

            //解析上传成功的结果
            DefaultPutRet putRet = FastJsonUtil.json2Bean(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);

            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 上传输入流
     * @param bucketNm  bucket的名称
     * @param in        输入流
     * @return
     */
    public static String upload(String bucketNm, InputStream in, String key) {
        try {

            //获取token
            String token = getToken(bucketNm);

            //上传输入流
            Response response = uploadManager().put(in,key,token, null,null);

            //解析上传成功的结果
            DefaultPutRet putRet = FastJsonUtil.json2Bean(response.bodyString(), DefaultPutRet.class);

            return putRet.key;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传输入流
     * @param in        输入流
     * @return
     */
    public static String upload(InputStream in, String key) {
        String url = "";

        try {

            //获取token
            String token = getToken(bucket);

            //上传输入流
            Response response = uploadManager().put(in,key,token, null,null);

            //解析上传成功的结果
            DefaultPutRet putRet = FastJsonUtil.json2Bean(response.bodyString(), DefaultPutRet.class);

            url = prefix + "/" + putRet.key;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 删除bucket中的文件名称
     * @param bucketNm bucker名称
     * @param key 文件名称
     * @return
     */
    public static boolean delete(String bucketNm ,String key) {
        try {
            bucketManager().delete(bucketNm, key);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteList(String bucketNm ,String[] keys){
        BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
        batchOperations.addDeleteOp(bucketNm, keys);
        try {
            Response response = bucketManager().batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            return true;
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取所有的bucket
     */
    public static String[] getBuckets() {
        try {
            //获取所有的bucket信息
            String[]  bucketNms = bucketManager().buckets();
            return bucketNms;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取bucket里面所有文件的信息
     * @param bucketNm 存储空间名
     */
    public static FileInfo[] getBucketList(String bucketNm,String prefix) {
        BucketManager.FileListIterator fileListIterator = bucketManager().createFileListIterator(bucketNm, prefix);

        if(fileListIterator.hasNext()){
            FileInfo[] files = fileListIterator.next();

            return files;
        }

        return null;

    }

    /**
     * 获取上传管理器
     * @return
     */
    private static UploadManager uploadManager() {
        UploadManager uploadManager = new UploadManager(cfg());
        return uploadManager;
    }

    /**
     * 获取Bucket的管理对象
     * @return
     */
    private static BucketManager bucketManager() {
        BucketManager bucketManager = new BucketManager(auth(), cfg());
        return bucketManager;
    }

    /**
     * 构造一个带指定Zone对象的配置类
     */
    private static Configuration cfg(){
        //构造一个带指定Zone对象的配置类
        //区域要和自己的bucket对上，不然就上传不成功
        //华东    Zone.zone0()
        //华北    Zone.zone1()
        //华南    Zone.zone2()
        //北美    Zone.zoneNa0()
        Configuration cfg = new Configuration(Zone.zone0());
        return cfg;
    }

    /**
     * 获取七牛云的上传Token
     * @param bucketNm
     * @return
     */
    private static String getToken(String bucketNm) {
        String upToken = auth().uploadToken(bucketNm);
        return upToken;
    }

    /**
     * 创建Auth
     */
    private static Auth auth(){
        Auth auth = Auth.create(accessKey, secretKey);
        return auth;
    }

    /**
     * CDN相关功能的接口实现
     * @return
     */
    private static CdnManager cdnManager(){
        CdnManager c = new CdnManager(auth());
        return c;
    }

    /**
     * 分页查询
     * @param bucket 空间名
     * @param marker 上一次获取文件列表时返回的 marker
     * @param limit  每次迭代的长度限制，最大1000，推荐值 100
     * @return
     */
    public static FileListing listFiles(String bucket, String marker, int limit) {

        FileListing files = null;
        try {
            files = bucketManager().listFilesV2(bucket, null, marker, limit, null);
            return files;
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBaseURL(String bucket){
        String[] list = new String[0];
        try {
            list = bucketManager().domainList(bucket);

            return list[0];
        } catch (QiniuException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static CdnResult.RefreshResult refresh(String[] urls){
        try {
            CdnResult.RefreshResult result = cdnManager().refreshUrls(urls);
            return result;
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return null;
    }
}
