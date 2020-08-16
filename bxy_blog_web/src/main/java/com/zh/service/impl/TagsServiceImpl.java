package com.zh.service.impl;

import com.zh.pojo.Tags;
import com.zh.mapper.TagsMapper;
import com.zh.service.TagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
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
}
