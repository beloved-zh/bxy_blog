package com.zh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zh.pojo.Menu;
import com.zh.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Beloved
 * @since 2020-06-22
 */
public interface UserService extends IService<User>, UserDetailsService {


//    List<User> getUserByUserName(String username);

    Boolean updateLogin(String id, Integer loginCount, String lastLoginTime,String lastLoginIp);

    User getUserByUserNameAndSource(String username,String source);


    IPage<User> getUser(
            String keyword,
            String role_id,
            String source,
            Boolean isLock,
            Boolean commentStatus,
            Integer currentPage,
            Integer pageSize);
}
