package com.zh.service.impl;

import com.zh.pojo.Tags;
import com.zh.mapper.TagsMapper;
import com.zh.service.TagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {

}
