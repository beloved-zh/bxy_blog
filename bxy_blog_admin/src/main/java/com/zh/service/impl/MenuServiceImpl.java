package com.zh.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.mapper.RoleMapper;
import com.zh.pojo.Menu;
import com.zh.mapper.MenuMapper;
import com.zh.pojo.Role;
import com.zh.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
@SuppressWarnings("ALL")
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Menu> getMenuByUserName(String username) {
        List<Menu> list = menuMapper.getFatherMenuByUserName(username);

        for (Menu menu : list) {
            List<Menu> childrenList = menuMapper.getChildrenMenuByMenuId(menu.getId());
            menu.setChildren(childrenList);
        }

        return list;
    }

    @Override
    public IPage<Menu> getMenu(String keyword,
                               Integer menuType,
                               Integer currentPage,
                               Integer pageSize) {

        // 查询父元素
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.like("menu_title",keyword);
        wrapper.eq("menu_type",menuType);
        wrapper.isNull("menu_id");
        wrapper.orderByAsc("sort");

        Page<Menu> page = new Page<>(currentPage,pageSize);

        IPage<Menu> iPage = menuMapper.getMenu(page, wrapper);

        for (Menu menu : iPage.getRecords()) {
            List<Menu> childrenList = menuMapper.getChildrenMenuByMenuId(menu.getId());
            for (Menu menu1 : childrenList) {
                List<Role> roleList = roleMapper.getRolesByMenuId(menu1.getId());
                menu1.setRoles(roleList);
            }
            menu.setChildren(childrenList);

            List<Role> roles = roleMapper.getRolesByMenuId(menu.getId());
            menu.setRoles(roles);
        }

        return iPage;
    }
}
