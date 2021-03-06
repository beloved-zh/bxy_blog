package com.zh.mapper;

import com.zh.pojo.Tags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
public interface TagsMapper extends BaseMapper<Tags> {

    List<Tags> findByBlogId(String blogId);


}
