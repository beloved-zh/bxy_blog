package com.zh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.mapper.BlogTagMapper;
import com.zh.pojo.Blog;
import com.zh.pojo.BlogTag;
import com.zh.service.BlogTagService;
import org.springframework.stereotype.Service;

/**
 * @author Beloved
 * @date 2020/7/20 16:18
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements BlogTagService {
}
