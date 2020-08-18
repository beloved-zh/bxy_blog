package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zh.VO.ResultVO;
import com.zh.pojo.User;
import com.zh.pojo.UserRole;
import com.zh.service.UserService;
import com.zh.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/updateInfo")
    public String updateInfo(
            String id,
            String avatar,
            String email,
            String mobile,
            String username,
            HttpServletRequest request){

        // 请求体获取tonke
        String token = request.getHeader(jwtTokenUtil.HEAD);
        // 去除前缀
        token = token.substring(jwtTokenUtil.HEAD_Prefix.length());
        // 从token中获取用户名
        String onLineUsername = jwtTokenUtil.getUsernameFromToken(token);

        User byUserName = userService.getUserByUserName(username);
        if (byUserName != null &&  !onLineUsername.equals(username)){
            return ResultVO.failure(400,"用户"+username+"以存在");
        }

        String avatarUrl = avatar;

        if (!StringUtil.validURL(avatar)){
            // Base64字符转换字节输入流
            InputStream inputStream = BaseUtil.BaseToInputStream(avatar);
            // 生成文件名
            String imageName = UUIDUtil.getUUID();

            avatarUrl = QiNiuUtil.upload(inputStream, imageName);

            if (StringUtils.isEmpty(avatarUrl)){
                return ResultVO.failure(400,"头像上传失败");
            }
        }

        User user = new User();
        user.setId(id);
        user.setUserName(username);
        user.setEmail(email);
        user.setMobile(mobile);
        user.setAvatar(avatarUrl);

        boolean b = userService.updateById(user);

        if (!b){
            return ResultVO.failure(400,"修改失败");
        }

        return ResultVO.ok("修改成功");
    }

}

