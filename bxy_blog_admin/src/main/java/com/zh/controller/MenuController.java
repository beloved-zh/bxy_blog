package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.VO.ResultVO;
import com.zh.pojo.Menu;
import com.zh.pojo.MenuRole;
import com.zh.pojo.Role;
import com.zh.service.MenuRoleService;
import com.zh.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRoleService menuRoleService;

    @GetMapping("/findAll")
    public String findAll(){

        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.isNull("menu_id");

        List<Menu> list = menuService.list(wrapper);

        for (Menu menu : list) {
            QueryWrapper<Menu> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("menu_id",menu.getId());
            List<Menu> children = menuService.list(wrapper2);
            menu.setChildren(children);

        }

        return ResultVO.ok(list);
    }

    @PutMapping("/updateMenu")
    public String updateMenu(
            String id,
            String menuTitle,
            String menuIcon,
            String menuUrl,
            String component,
            String menuId,
            String redirect,
            Integer sort,
            Boolean isShow,
            Boolean affix,
            Boolean menuType,
            String[] roles){

        Menu menu = new Menu();
        menu.setId(id);
        menu.setMenuTitle(menuTitle);
        menu.setMenuIcon(menuIcon);
        menu.setMenuUrl(menuUrl);
        menu.setComponent(component);
        menuId = menuId.equals("") || menuId.equals("undefined") ? null : menuId;
        menu.setMenuId(menuId);
        menu.setRedirect(redirect);
        menu.setSort(sort);
        menu.setIsShow(!isShow);
        menu.setAffix(affix);
        menu.setMenuType(!menuType);

        boolean b = menuService.updateById(menu);

        if (b){
            QueryWrapper<MenuRole> wrapper = new QueryWrapper<>();
            wrapper.eq("menu_id",id);
            boolean remove = menuRoleService.remove(wrapper);
            if (remove){
                for (String role : roles) {
                    MenuRole menuRole = new MenuRole();
                    menuRole.setMenuId(id);
                    menuRole.setRoleId(role);
                    menuRoleService.save(menuRole);
                }
                return ResultVO.ok("修改成功");
            }
            return ResultVO.failure(400,"修改失败");
        }
        return ResultVO.failure(400,"修改失败");
    }

    @DeleteMapping("/deleteMenu")
    public String deleteMenu(String id){
        QueryWrapper<MenuRole> wrapper = new QueryWrapper<>();
        wrapper.eq("menu_id",id);
        boolean remove = menuRoleService.remove(wrapper);
        if (remove){
            boolean b = menuService.removeById(id);
            if (b){
                return ResultVO.ok("删除成功");
            }
            return ResultVO.failure(400,"删除失败");
        }
        return ResultVO.failure(400,"删除失败");

    }

    @PostMapping("/addMenu")
    public String addMenu(
            String menuTitle,
            String menuIcon,
            String menuUrl,
            String component,
            String menuId,
            String redirect,
            Integer sort,
            Boolean isShow,
            Boolean affix,
            Boolean menuType,
            String[] roles){

        Menu menu = new Menu();
        menu.setMenuTitle(menuTitle);
        menu.setMenuIcon(menuIcon);
        menu.setMenuUrl(menuUrl);
        menu.setComponent(component);
        menuId = menuId.equals("") || menuId.equals("undefined")  ? null : menuId;
        menu.setMenuId(menuId);
        menu.setRedirect(redirect);
        menu.setSort(sort);
        menu.setIsShow(!isShow);
        menu.setAffix(affix);
        menu.setMenuType(!menuType);

        menuService.save(menu);

        /*
         * mybatis-plus
         * id使用uuid每当新增数据之后
         * 执行完之后会将主键值设置到传入实体类的主键属性中。
         * 在方法执行完之后获取实体类的主键就获取新增的数据id
         */
        String id = menu.getId();

        for (String role : roles) {
            MenuRole menuRole = new MenuRole();
            menuRole.setMenuId(id);
            menuRole.setRoleId(role);
            menuRoleService.save(menuRole);
        }

        return ResultVO.ok("添加成功");
    }

    @GetMapping("/getMenus")
    public String getMenus(
            String keyword,
            Boolean menuType,
            Integer currentPage,
            Integer pageSize){

        Integer menuTypeInt = menuType ? 1 : 0;

        IPage<Menu> page = menuService.getMenu(keyword, menuTypeInt, currentPage, pageSize);


        return ResultVO.ok(page);
    }

}

