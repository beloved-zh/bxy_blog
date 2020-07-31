package com.zh.service.impl;

import com.zh.pojo.Sort;
import com.zh.mapper.SortMapper;
import com.zh.service.SortService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
@SuppressWarnings("ALL")
@Service
public class SortServiceImpl extends ServiceImpl<SortMapper, Sort> implements SortService {

    @Autowired
    private SortMapper sortMapper;

    @Override
    public List<Map<String, Object>> getBlogCountByBlogSort() {
        return sortMapper.getBlogCountByBlogSort();
    }
}
