package com.zh.mapper;

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
 * @since 2020-08-05
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRolesByUserName(@Param("userName") String username);

}
