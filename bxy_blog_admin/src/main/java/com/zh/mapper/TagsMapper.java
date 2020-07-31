package com.zh.mapper;

import com.zh.pojo.Tags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
public interface TagsMapper extends BaseMapper<Tags> {

    List<Tags> findByBlogId(String blogId);

    List<Map<String, Object>> getBlogCountByTag();
}
