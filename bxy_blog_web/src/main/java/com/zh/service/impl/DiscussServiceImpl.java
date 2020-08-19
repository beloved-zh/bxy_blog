package com.zh.service.impl;

import com.zh.pojo.Discuss;
import com.zh.mapper.DiscussMapper;
import com.zh.service.DiscussService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
@SuppressWarnings("ALL")
@Service
public class DiscussServiceImpl extends ServiceImpl<DiscussMapper, Discuss> implements DiscussService {

    @Autowired
    private DiscussMapper discussMapper;

    @Override
    public List<Discuss> getDiscuss(String blogId) {

        List<Discuss> list = discussMapper.getDiscussFather(blogId);

        for (Discuss discuss : list) {
            List<Discuss> children = discussMapper.getDiscussSon(discuss.getId());
            discuss.setChildren(children);
        }

        return list;
    }

    @Override
    public List<Discuss> getDiscussByUser(String userId) {
        return discussMapper.getDiscussByUser(userId);
    }
}
