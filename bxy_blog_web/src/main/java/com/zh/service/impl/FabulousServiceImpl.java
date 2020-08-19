package com.zh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.mapper.DiscussMapper;
import com.zh.mapper.FabulousMapper;
import com.zh.pojo.Discuss;
import com.zh.pojo.Fabulous;
import com.zh.service.DiscussService;
import com.zh.service.FabulousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 点赞表 服务实现类
 * </p>
 * @author Beloved
 * @date 2020/8/19 15:38
 */
@SuppressWarnings("ALL")
@Service
public class FabulousServiceImpl extends ServiceImpl<FabulousMapper, Fabulous> implements FabulousService {

    @Autowired
    private FabulousMapper fabulousMapper;

    @Override
    public List<Fabulous> getFabulousByUser(String userId) {
        return fabulousMapper.getFabulousByUser(userId);
    }
}
