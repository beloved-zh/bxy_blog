package com.zh.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.pojo.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 博客表 Mapper 接口
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
public interface BlogMapper extends BaseMapper<Blog> {
    // 自定义条件构造器和分页插件 需要mybatisplus在3.0.7以上
    IPage<Blog> getBlog(Page<Blog> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
