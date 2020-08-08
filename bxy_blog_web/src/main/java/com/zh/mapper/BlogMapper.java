package com.zh.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.pojo.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 博客表 Mapper 接口
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
public interface BlogMapper extends BaseMapper<Blog> {

    List<Blog> getBlogByLevel(@Param("level")Integer level);

    List<Blog> getBlogByTop();

    // 自定分页插件 需要mybatisplus在3.0.7以上
    IPage<Blog> getBlogByLevelAndPage(Page<Blog> page,@Param("level")Integer level);

    Blog getBlogById(@Param("id")String id);
}
