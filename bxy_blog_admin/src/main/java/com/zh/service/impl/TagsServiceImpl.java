package com.zh.service.impl;

import com.zh.pojo.Tags;
import com.zh.mapper.TagsMapper;
import com.zh.service.TagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
@SuppressWarnings("ALL")
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {

    @Autowired
    private TagsMapper tagsMapper;

    @Override
    public List<Tags> findByBlogId(String blogId) {
        return tagsMapper.findByBlogId(blogId);
    }

    @Override
    public List<Map<String, Object>> getBlogCountByTag() {
        return tagsMapper.getBlogCountByTag();
    }
}
