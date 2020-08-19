package com.zh.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.pojo.Blog;
import com.zh.mapper.BlogMapper;
import com.zh.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 博客表 服务实现类
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
@SuppressWarnings("ALL")
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<Blog> getBlogByLevel(Integer level) {
        return blogMapper.getBlogByLevel(level);
    }

    @Override
    public List<Blog> getBlogByTop() {
        return blogMapper.getBlogByTop();
    }

    @Override
    public Blog getBlogById(String id) {
        return blogMapper.getBlogById(id);
    }

    @Override
    public List<Blog> getBlogBySortId(String sortId) {
        return blogMapper.getBlogBySortId(sortId);
    }

    @Override
    public List<Blog> getBlogByTagId(String tagId) {
        return blogMapper.getBlogByTagId(tagId);
    }

    @Override
    public String getOneBlogCreateTime() {
        return blogMapper.getOneBlogCreateTime();
    }

    @Override
    public List<Blog> getBlogByMonth(String month) {
        return blogMapper.getBlogByMonth(month);
    }

    @Override
    public IPage<Blog> getBlogByLevelAndPage(Integer level, Integer currentPage, Integer pageSize) {

        Page<Blog> page = new Page<>(currentPage,pageSize);

        return blogMapper.getBlogByLevelAndPage(page,level);
    }

    @Override
    public Boolean updateClickCount(String id) {
        return blogMapper.updateClickCount(id);
    }
}
