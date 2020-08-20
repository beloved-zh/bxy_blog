package com.zh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.mapper.RoleMapper;
import com.zh.pojo.Menu;
import com.zh.pojo.Role;
import com.zh.pojo.User;
import com.zh.mapper.UserMapper;
import com.zh.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Beloved
 * @since 2020-06-22
 */
@SuppressWarnings("ALL")
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


//    @Override
//    public List<User> getUserByUserName(String username) {
//
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//
//        wrapper.eq("user_name",username);
//
//        List<User> list = userMapper.selectList(wrapper);
//
//
//        return list;
//    }

    @Override
    public Boolean updateLogin(String id, Integer loginCount, String lastLoginTime, String lastLoginIp) {
        return userMapper.updateLogin(id, loginCount, lastLoginTime, lastLoginIp);
    }

    @Override
    public User getUserByUserNameAndSource(String username,String source) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.eq("user_name",username);
        wrapper.eq("source",source);

        User user = userMapper.selectOne(wrapper);

        return user;
    }

    @Override
    public IPage<User> getUser(String keyword, String role_id, String source, Boolean isLock, Boolean commentStatus, Integer currentPage, Integer pageSize) {

        Page<User> page = new Page<>(currentPage,pageSize);

        QueryWrapper<Object> wrapper = new QueryWrapper<>();
        wrapper.like("u.user_name",keyword);
        if (!StringUtils.isEmpty(role_id)){
            wrapper.eq("r.id",role_id);
        }
        if (!StringUtils.isEmpty(source)){
            wrapper.eq("u.source",source);
        }
        if (!StringUtils.isEmpty(isLock)){
            Integer is_lock = isLock ? 0 : 1;
            wrapper.eq("u.is_lock",is_lock);
        }
        if (!StringUtils.isEmpty(commentStatus)){
            Integer comment_status = commentStatus ? 0 : 1;
            wrapper.eq("u.comment_status",comment_status);
        }


        IPage<User> iPage = userMapper.getUser(page, wrapper);

        return iPage;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("进入查询"+username);
//        User user = getUserByUserName(username);
//
//        if (user == null) {
//
//            throw new UsernameNotFoundException("用户名或密码错误");
//
//        }else {
//            System.out.println("进入查询角色" + username);
//            //查找角色
//            List<Role> roles = roleMapper.getRolesByUserName(username);
//
//            System.out.println(roles);
//
//            user.setRoles(roles);
//
//            return user;
//        }
        return null;
    }
}
