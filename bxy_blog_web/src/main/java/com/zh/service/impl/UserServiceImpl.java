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
    public User getUserByUserName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.eq("user_name",username);

        User user = userMapper.selectOne(wrapper);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUserName(username);

        if (user == null) {

            throw new UsernameNotFoundException("用户名或密码错误");

        }else {
            //查找角色
            List<Role> roles = roleMapper.getRolesByUserName(username);

            user.setRoles(roles);

            return user;
        }
    }
}
