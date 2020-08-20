package com.zh.mapper;

import com.zh.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
public interface UserMapper extends BaseMapper<User> {

    Boolean updateLogin(String id, Integer loginCount, String lastLoginTime,String lastLoginIp);

}
