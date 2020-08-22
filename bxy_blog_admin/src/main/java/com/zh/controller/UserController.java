package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zh.VO.ResultVO;
import com.zh.pojo.MenuRole;
import com.zh.pojo.User;
import com.zh.pojo.UserRole;
import com.zh.service.UserRoleService;
import com.zh.service.UserService;
import com.zh.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private QiNiuUtil qiNiuUtil;

    @GetMapping("/getUser")
    public String getUser(
            String keyword,
            String role_id,
            String source,
            Boolean isLock,
            Boolean commentStatus,
            Integer currentPage,
            Integer pageSize){


        IPage<User> page = userService.getUser(keyword, role_id, source, isLock, commentStatus, currentPage, pageSize);

        return ResultVO.ok(page);
    }

    @PostMapping("/addUser")
    public String addUser(
            String userName,
            String password,
            String role,
            String email,
            String mobile,
            String avatar) throws IOException {

        if (userService.getUserByUserNameAndSource(userName,"BXY") != null){
            return ResultVO.failure(400,"用户"+userName+"以存在");
        }

        // Base64字符转换字节输入流
        InputStream inputStream = BaseUtil.BaseToInputStream(avatar);
        // 生成文件名
        String imageName = UUIDUtil.getUUID() + ".png";

        String avatarUrl = qiNiuUtil.upload(inputStream, imageName);
        if (StringUtils.isEmpty(avatarUrl)){
            return ResultVO.failure(400,"头像上传失败");
        }

        User user = new User();
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setMobile(mobile);
        user.setSource("BXY");
        user.setAvatar(avatarUrl);


        boolean b = userService.save(user);

        if (!b){
            return ResultVO.failure(400,"用户添加失败");
        }

        UserRole userRole = new UserRole();
        userRole.setRoleId(role);
        userRole.setUserId(user.getId());

        boolean save = userRoleService.save(userRole);

        if (!save){
            return ResultVO.failure(400,"用户角色添加失败");
        }

        return ResultVO.ok("添加成功");
    }

    @PutMapping("/updateUser")
    public String updateUser(
            String id,
            String userName,
            String role,
            String email,
            String mobile,
            String avatar,
            Boolean isLock,
            Boolean commentStatus,
            String source,
            HttpServletRequest request) throws IOException {

        User userOld = userService.getUserByUserNameAndSource(userName, "BXY");

//        if (userOld != null && ){
//            return ResultVO.failure(400,"用户"+userName+"以存在");
//        }

        String avatarUrl = avatar;

        if (!StringUtil.validURL(avatar)){
            // Base64字符转换字节输入流
            InputStream inputStream = BaseUtil.BaseToInputStream(avatar);
            // 生成文件名
            String imageName = UUIDUtil.getUUID();

            avatarUrl = qiNiuUtil.upload(inputStream, imageName);

            if (StringUtils.isEmpty(avatarUrl)){
                return ResultVO.failure(400,"头像上传失败");
            }
        }

        User user = new User();
        user.setId(id);
        user.setUserName(userName);
        user.setEmail(email);
        user.setMobile(mobile);
        user.setAvatar(avatarUrl);
        user.setIsLock(!isLock);
        user.setCommentStatus(!commentStatus);

        boolean b = userService.updateById(user);

        if (!b){
            return ResultVO.failure(400,"用户修改失败");
        }

        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        boolean remove = userRoleService.remove(wrapper);
        if (remove){
            UserRole userRole = new UserRole();
            userRole.setRoleId(role);
            userRole.setUserId(user.getId());

            boolean save = userRoleService.save(userRole);

            if (!save){
                return ResultVO.failure(400,"用户角色添加失败");
            }
        }

        return ResultVO.ok("修改成功");
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(String id){
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        boolean remove = userRoleService.remove(wrapper);
        if (!remove){
            return ResultVO.failure(400,"删除失败");
        }
        boolean b = userService.removeById(id);
        if (!b) {
            return ResultVO.failure(400,"删除失败");
        }
        return ResultVO.ok("删除成功");
    }
}

