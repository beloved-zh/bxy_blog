package com.zh.service;

import com.zh.pojo.Discuss;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
public interface DiscussService extends IService<Discuss> {

    List<Discuss> getDiscuss(String blogId);

    List<Discuss> getDiscussByUser(String userId);
}
