package com.zh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zh.pojo.Menu;
import com.zh.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Beloved
 * @since 2020-06-22
 */
public interface UserService extends IService<User>, UserDetailsService {

    /**
     * 通过账号查询用户
     * @param username
     * @return
     */
    User getUserByUserName(String username);


    IPage<User> getUser(
            String keyword,
            String role_id,
            String source,
            Boolean isLock,
            Boolean commentStatus,
            Integer currentPage,
            Integer pageSize);
}
