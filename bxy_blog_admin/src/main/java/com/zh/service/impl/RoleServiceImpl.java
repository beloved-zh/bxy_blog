package com.zh.service.impl;

import com.zh.pojo.Role;
import com.zh.mapper.RoleMapper;
import com.zh.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Beloved
 * @since 2020-06-22
 */
@SuppressWarnings("ALL")
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> getRoleNameByUserName(String username) {

        List<Role> roles = roleMapper.getRolesByUserName(username);

        ArrayList<String> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(role.getRoleName());
        }

        return list;
    }
}
