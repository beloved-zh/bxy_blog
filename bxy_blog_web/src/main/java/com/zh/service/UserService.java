package com.zh.service;

import com.zh.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
public interface UserService extends IService<User>, UserDetailsService {

    User getUserByUserName(String username);

}
