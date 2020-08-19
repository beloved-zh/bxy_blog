package com.zh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.mapper.FabulousMapper;
import com.zh.pojo.Fabulous;
import com.zh.service.FabulousService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 点赞表 服务实现类
 * </p>
 * @author Beloved
 * @date 2020/8/19 15:38
 */
@Service
public class FabulousServiceImpl extends ServiceImpl<FabulousMapper, Fabulous> implements FabulousService {
}
