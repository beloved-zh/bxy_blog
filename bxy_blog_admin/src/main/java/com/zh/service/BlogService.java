package com.zh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zh.pojo.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 博客表 服务类
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
public interface BlogService extends IService<Blog> {

    IPage<Blog> getBlog(String keyword,
                        String sortId,
                        String tagId,
                        Integer level,
                        Boolean status,
                        Boolean original,
                        Integer currentPage,
                        Integer pageSize);

}
