package com.zh.service;

import com.zh.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author Beloved
 * @since 2020-06-22
 */
public interface RoleService extends IService<Role> {

    List<String> getRoleNameByUserName(String username);

}
