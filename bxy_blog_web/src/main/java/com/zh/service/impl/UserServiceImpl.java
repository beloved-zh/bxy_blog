package com.zh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zh.mapper.RoleMapper;
import com.zh.pojo.Role;
import com.zh.pojo.User;
import com.zh.mapper.UserMapper;
import com.zh.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
@SuppressWarnings("ALL")
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User getUserByUserNameAndSource(String username,String source) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.eq("user_name",username);
        wrapper.eq("source",source);

        User user = userMapper.selectOne(wrapper);

        return user;
    }

    @Override
    public Boolean updateLogin(String id, Integer loginCount, String lastLoginTime, String lastLoginIp) {
        return userMapper.updateLogin(id, loginCount, lastLoginTime, lastLoginIp);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
