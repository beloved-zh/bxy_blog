package com.zh.mapper;

import com.zh.pojo.Sort;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
public interface SortMapper extends BaseMapper<Sort> {

    List<Map<String, Object>> getBlogCountByBlogSort();

}
