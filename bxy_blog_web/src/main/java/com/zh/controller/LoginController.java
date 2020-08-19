package com.zh.controller;

import com.zh.Enums.ResultEnum;
import com.zh.VO.ResultVO;
import com.zh.pojo.Menu;
import com.zh.pojo.User;
import com.zh.service.MenuService;
import com.zh.service.RoleService;
import com.zh.service.UserService;
import com.zh.utils.JwtTokenUtil;
import com.zh.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  登录授权操作前端控制器
 * </p>
 * @author Beloved
 * @date 2020/6/23 13:15
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/info")
    public String getInfo(HttpServletRequest request){

        String token = request.getHeader(jwtTokenUtil.HEAD);

        if (token != null && token.startsWith(jwtTokenUtil.HEAD_Prefix)) {

            // 去除前缀
            token = token.substring(jwtTokenUtil.HEAD_Prefix.length());

            // 从token中获取用户名
            String username = jwtTokenUtil.getUsernameFromToken(token);

            // 验证令牌
            if (redisUtil.hasKey(token)) {
                User user = userService.getUserByUserName(username);

                HashMap<String, Object> map = new HashMap<>();
                map.put("id",user.getId());
                map.put("username", user.getUsername());
                map.put("avatar", user.getAvatar());
                map.put("source", user.getSource());
                map.put("email", user.getEmail());
                map.put("mobile", user.getMobile());

                return ResultVO.ok(ResultEnum.SUCCESS,map);
            }

            return ResultVO.failure(ResultEnum.TOKEN_OVER);
        }

        return ResultVO.failure(ResultEnum.TOKEN_BAD);
    }


}
