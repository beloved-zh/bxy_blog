package com.zh.service.impl;

import com.zh.pojo.RequestApi;
import com.zh.mapper.RequestApiMapper;
import com.zh.service.RequestApiService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 请求地址表 服务实现类
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
@SuppressWarnings("ALL")
@Service
public class RequestApiServiceImpl extends ServiceImpl<RequestApiMapper, RequestApi> implements RequestApiService {

    @Autowired
    private RequestApiMapper requestApiMapper;

    @Override
    public List<RequestApi> getRequestApiByUserName(String username) {

        List<RequestApi> list = requestApiMapper.getRequestApiByUserName(username);

        return list;
    }
}
