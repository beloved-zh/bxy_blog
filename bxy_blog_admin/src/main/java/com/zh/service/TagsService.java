package com.zh.service;

import com.zh.pojo.Tags;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
public interface TagsService extends IService<Tags> {

    List<Tags> findByBlogId(String blogId);

}
