package com.zh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zh.pojo.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 博客表 服务类
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
public interface BlogService extends IService<Blog> {

    List<Blog> getBlogByLevel(Integer level);

    List<Blog> getBlogByTop();

    IPage<Blog> getBlogByLevelAndPage(
                        Integer level,
                        Integer currentPage,
                        Integer pageSize);
}
