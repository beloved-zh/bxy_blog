package com.zh.service;

import com.zh.pojo.RequestApi;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 请求地址表 服务类
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
public interface RequestApiService extends IService<RequestApi> {

    List<RequestApi> getRequestApiByUserName(String username);

}
