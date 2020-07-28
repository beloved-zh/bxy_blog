package com.zh.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.pojo.Menu;
import com.zh.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
public interface UserMapper extends BaseMapper<User> {

    // 自定义条件构造器和分页插件 需要mybatisplus在3.0.7以上
    IPage<User> getUser(Page<User> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}
