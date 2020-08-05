package com.zh.VO;

import com.zh.Enums.ResultEnum;
import com.zh.utils.FastJsonUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Beloved
 * @date 2020/6/22 15:13
 */
public final class ResultVO implements Serializable {

    /**
     * 默认成功状态
     * @return
     */
    public final static String ok() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", ResultEnum.SUCCESS.getCode());
        map.put("message", ResultEnum.SUCCESS.getMessage());
        return FastJsonUtil.map2Json(map);
    }

    public final static String ok(String message,Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", ResultEnum.SUCCESS.getCode());
        map.put("message", message);
        map.put("data", data);
        return FastJsonUtil.map2Json(map);
    }

    /**
     * 默认成功状态
     * @param data  携带数据
     * @return
     */
    public final static String ok(Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", ResultEnum.SUCCESS.getCode());
        map.put("message", ResultEnum.SUCCESS.getMessage());
        map.put("data", data);
        return FastJsonUtil.map2Json(map);
    }

    /**
     * 默认失败状态
     * @return
     */
    public final static String failure() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", ResultEnum.FAILURE.getCode());
        map.put("message", ResultEnum.FAILURE.getMessage());
        return FastJsonUtil.map2Json(map);
    }

    /**
     * 默认失败状态
     * @param data  携带数据
     * @return
     */
    public final static String failure(Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", ResultEnum.FAILURE.getCode());
        map.put("message", ResultEnum.FAILURE.getMessage());
        map.put("data", data);
        return FastJsonUtil.map2Json(map);
    }

    /**
     * 使用枚举
     * @param code 枚举状态码
     * @return
     */
    public final static String failure(ResultEnum code) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code.getCode());
        map.put("message", code.getMessage());
        return FastJsonUtil.map2Json(map);
    }

    public final static String failure(Integer code,String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("message", message);
        return FastJsonUtil.map2Json(map);
    }

    /**
     * 使用枚举
     * @param code 枚举状态码
     * @return
     */
    public final static String ok(ResultEnum code) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code.getCode());
        map.put("message", code.getMessage());
        return FastJsonUtil.map2Json(map);
    }


    /**
     *
     *
     * @param data@param code
     * @return
     */
    public final static String ok(ResultEnum code, Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code.getCode());
        map.put("message", code.getMessage());
        map.put("data", data);
        return FastJsonUtil.map2Json(map);
    }

    /**
     * 自定义
     * @param code
     * @param message
     * @return
     */
    public final static String ok(Integer code,String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("message", message);
        return FastJsonUtil.map2Json(map);
    }

    /**
     * 自定义
     * @param code
     * @param message
     * @param data
     * @return
     */
    public final static String ok(Integer code,String message, Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("message", message);
        map.put("data", data);
        return FastJsonUtil.map2Json(map);
    }


}
