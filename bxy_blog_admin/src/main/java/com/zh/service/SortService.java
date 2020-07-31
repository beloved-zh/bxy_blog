package com.zh.service;

import com.zh.pojo.Sort;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
public interface SortService extends IService<Sort> {

    List<Map<String, Object>> getBlogCountByBlogSort();

}
