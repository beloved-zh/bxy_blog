package com.zh.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过用户名获取父亲菜单
     * @param username
     * @return
     */
    List<Menu> getFatherMenuByUserName(@Param("username") String username);

    List<Menu> getChildrenMenuByMenuId(@Param("id") String id);

    // 自定义条件构造器和分页插件 需要mybatisplus在3.0.7以上
    IPage<Menu> getMenu(Page<Menu> page, @Param(Constants.WRAPPER) Wrapper<Menu> wrapper);

}
