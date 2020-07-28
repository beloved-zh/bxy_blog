package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.VO.ResultVO;
import com.zh.pojo.Role;
import com.zh.service.RoleService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/role")
public class RoleController {


    @Autowired
    private RoleService roleService;

    @GetMapping("/findAll")
    public String findAll(){

        List<Role> list = roleService.list();

        return ResultVO.ok(list);
    }

    @GetMapping("/getRoles")
    public String getRoles(
            String keyword,
            Integer currentPage,
            Integer pageSize){

        QueryWrapper<Role> wrapper = new QueryWrapper<>();

        wrapper.like("role_name",keyword);

        Page<Role> page = new Page<>(currentPage,pageSize);

        roleService.page(page, wrapper);

        return ResultVO.ok(page);
    }

    public boolean judgeRoleName(String roleName){
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name",roleName);
        Role one = roleService.getOne(wrapper);
        if (one != null){
            return true;
        }
        return false;
    }

    @PostMapping("/addRole")
    public String addRole(String roleName, String summary){

        if (judgeRoleName(roleName)){
            return ResultVO.failure(400,"该角色已存在");
        }

        Role role = new Role();
        role.setRoleName(roleName);
        role.setSummary(summary);

        boolean b = roleService.save(role);

        if (b){
            return ResultVO.ok("添加成功");
        }
        return ResultVO.failure(400,"添加失败");
    }

    @PutMapping("/updateRole")
    public String updateRole(String id,String roleName, String summary){

        Role role1 = roleService.getById(id);
        if (!role1.getRoleName().equals(roleName)){
            if (judgeRoleName(roleName)){
                return ResultVO.failure(400,"该角色已存在");
            }
        }

        Role role = new Role();
        role.setId(id);
        role.setRoleName(roleName);
        role.setSummary(summary);

        boolean b = roleService.updateById(role);

        if (b){
            return ResultVO.ok("修改成功");
        }
        return ResultVO.failure(400,"修改失败");
    }

    @DeleteMapping("/deleteRole")
    public String deleteRole(String id){

        boolean b = roleService.removeById(id);

        if (b){
            return ResultVO.ok("删除成功");
        }
        return ResultVO.failure(400,"删除失败");
    }

    @DeleteMapping("/deleteRoleList")
    public String deleteRoleList(String ids){

        List<String> list = Arrays.asList(ids.split(","));


        boolean b = roleService.removeByIds(list);

        if (b){
            return ResultVO.ok("删除成功");
        }
        return ResultVO.failure(400,"删除失败");
    }
}

