package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.VO.ResultVO;
import com.zh.mapper.RoleMapper;
import com.zh.pojo.RequestApi;
import com.zh.pojo.RequestApiRole;
import com.zh.pojo.Role;
import com.zh.service.RequestApiRoleService;
import com.zh.service.RequestApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 请求地址表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api")
public class RequestApiController {

    @Autowired
    private RequestApiService apiService;

    @Autowired
    private RequestApiRoleService apiRoleService;

    @Autowired
    private RoleMapper roleMapper;

    @GetMapping("/getFatherApi")
    public String getFatherApi(){
        QueryWrapper<RequestApi> wrapper = new QueryWrapper<>();
        wrapper.isNull("request_id");

        List<RequestApi> list = apiService.list(wrapper);

        return ResultVO.ok(list);
    }

    @DeleteMapping("/deleteApi")
    public String deleteApi(String id){
        QueryWrapper<RequestApiRole> wrapper = new QueryWrapper<>();
        wrapper.eq("r_api_id",id);
        boolean remove = apiRoleService.remove(wrapper);
        if (!remove){
            return ResultVO.failure(400,"角色删除失败");
        }

        boolean b = apiService.removeById(id);
        if (!b){
            return ResultVO.failure(400,"删除失败");
        }

        return ResultVO.ok("删除成功");
    }

    @PutMapping("/updateApi")
    public String updateApi(
            String id,
            String url,
            String method,
            Boolean apiType,
            String summary,
            String requestId,
            String roles){
        RequestApi api = new RequestApi();
        api.setId(id);
        api.setUrl(url);
        api.setMethod(method);
        api.setApiType(!apiType);
        api.setSummary(summary);
        api.setRequestId(StringUtils.isEmpty(requestId) ? null : requestId);

        boolean b = apiService.updateById(api);
        if (!b){
            return ResultVO.failure(400,"修改接口失败");
        }

        QueryWrapper<RequestApiRole> wrapper = new QueryWrapper<>();
        wrapper.eq("r_api_id",id);
        boolean remove = apiRoleService.remove(wrapper);
        if (!remove){
            return ResultVO.failure(400,"修改接口失败");
        }

        String[] roleArr = roles.split(",");
        for (String role : roleArr) {
            RequestApiRole apiRole = new RequestApiRole();
            apiRole.setRoleId(role);
            apiRole.setRApiId(api.getId());

            boolean save = apiRoleService.save(apiRole);
            if (!save){
                return ResultVO.failure(400,"添加角色失败");
            }
        }
        return ResultVO.ok("修改成功");
    }

    @PostMapping("/addApi")
    public String addApi(
            String url,
            String method,
            Boolean apiType,
            String summary,
            String requestId,
            String roles){

        RequestApi api = new RequestApi();
        api.setUrl(url);
        api.setMethod(method);
        api.setApiType(!apiType);
        api.setSummary(summary);
        api.setRequestId(StringUtils.isEmpty(requestId) ? null : requestId);

        boolean save = apiService.save(api);

        if (!save){
            return ResultVO.failure(400,"添加接口失败");
        }

        String[] roleArr = roles.split(",");
        for (String role : roleArr) {
            RequestApiRole apiRole = new RequestApiRole();
            apiRole.setRoleId(role);
            apiRole.setRApiId(api.getId());

            boolean b = apiRoleService.save(apiRole);
            if (!b){
                return ResultVO.failure(400,"添加角色失败");
            }
        }

        return ResultVO.ok("添加成功");
    }

    @GetMapping("/getApi")
    public String getApi(
            String keyword,
            Boolean type,
            Integer currentPage,
            Integer pageSize){

        Integer apiType = type ? 1 : 0;

        QueryWrapper<RequestApi> wrapper = new QueryWrapper<>();
        wrapper.like("url",keyword);
        wrapper.eq("api_type",apiType);
        wrapper.isNull("request_id");

        Page<RequestApi> page = new Page<>(currentPage,pageSize);

        apiService.page(page,wrapper);

        for (RequestApi api : page.getRecords()) {
            QueryWrapper<RequestApi> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("request_id",api.getId());
            List<RequestApi> list = apiService.list(wrapper1);
            for (RequestApi requestApi : list) {
                List<Role> roles = roleMapper.getRolesByApiId(requestApi.getId());
                requestApi.setRoles(roles);
            }
            api.setChildren(list);

            List<Role> roles = roleMapper.getRolesByApiId(api.getId());
            api.setRoles(roles);
        }

        return ResultVO.ok(page);
    }

}

