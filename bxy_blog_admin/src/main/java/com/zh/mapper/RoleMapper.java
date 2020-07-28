package com.zh.mapper;

import com.zh.pojo.RequestApi;
import com.zh.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author Beloved
 * @since 2020-06-22
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户名查询角色
     * @param username
     * @return
     */
    List<Role> getRolesByUserName(@Param("userName") String username);

    /**
     * 根据菜单id查询角色
     * @param id
     * @return
     */
    List<Role> getRolesByMenuId(@Param("id") String id);

    /**
     * 根据接口id查询角色
     * @param id
     * @return
     */
    List<Role> getRolesByApiId(@Param("id") String id);
}
