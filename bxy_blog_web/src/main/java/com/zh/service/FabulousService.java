package com.zh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.pojo.Fabulous;
import com.zh.pojo.SystemConfig;

import java.util.List;

/**
 * <p>
 * 点赞表 服务类
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
public interface FabulousService extends IService<Fabulous> {

    List<Fabulous> getFabulousByUser(String userId);

}
