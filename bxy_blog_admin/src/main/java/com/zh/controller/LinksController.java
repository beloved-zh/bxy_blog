package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.VO.ResultVO;
import com.zh.pojo.Links;
import com.zh.pojo.User;
import com.zh.service.LinksService;
import com.zh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 友情链接表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/links")
public class LinksController {

    @Autowired
    private LinksService linksService;

    @Autowired
    private UserService userService;

    @PutMapping("/updateLink")
    public String updateLink(
            String id,
            String linkName,
            String linkUrl,
            String summary,
            Boolean linkStatus,
            Integer sort,
            String userId){

        Links link = new Links();
        link.setId(id);
        link.setLinkName(linkName);
        link.setLinkUrl(linkUrl);
        link.setSummary(summary);
        link.setLinkStatus(linkStatus);
        link.setSort(sort);
        link.setUserId(userId);

        boolean b = linksService.updateById(link);

        if (b){
            return ResultVO.ok("修改成功");
        }
        return ResultVO.failure(400,"修改失败");
    }

    @DeleteMapping("/deleteLink")
    public String deleteLink(String id){

        boolean b = linksService.removeById(id);

        if (b){
            return ResultVO.ok("删除成功");
        }
        return ResultVO.failure(400,"删除失败");
    }

    @PostMapping("/addLink")
    public String addLink(
            String linkName,
            String linkUrl,
            String summary,
            Boolean linkStatus,
            Integer sort,
            String userId){

        Links link = new Links();
        link.setLinkName(linkName);
        link.setLinkUrl(linkUrl);
        link.setSummary(summary);
        link.setLinkStatus(linkStatus);
        link.setSort(sort);
        link.setUserId(userId);

        boolean save = linksService.save(link);

        if (save){
            return ResultVO.ok("添加成功");
        }
        return ResultVO.failure(400,"添加失败");
    }

    @GetMapping("/getLinks")
    public String getLinks(
            String keyword,
            Boolean linkStatus,
            Integer currentPage,
            Integer pageSize){

        QueryWrapper<Links> wrapper = new QueryWrapper<>();

        wrapper.like("link_name",keyword);
        wrapper.orderByAsc("sort");

        if (linkStatus!=null){
            Integer status = linkStatus ? 1 : 0;
            wrapper.eq("link_status",status);
        }

        Page<Links> page = new Page<>(currentPage,pageSize);

        linksService.page(page, wrapper);

        for (Links link : page.getRecords()) {
            QueryWrapper<User> wrapperUser = new QueryWrapper<>();
            wrapperUser.eq("id",link.getUserId());
            wrapperUser.select("id","user_name","avatar","is_lock");
            User user = userService.getOne(wrapperUser);
            link.setUser(user);
        }

        return ResultVO.ok(page);
    }
}

