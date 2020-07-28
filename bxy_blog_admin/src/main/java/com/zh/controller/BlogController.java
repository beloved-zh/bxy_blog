package com.zh.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.VO.ResultVO;
import com.zh.pojo.Blog;
import com.zh.pojo.BlogTag;
import com.zh.pojo.Tags;
import com.zh.service.BlogService;
import com.zh.service.BlogTagService;
import com.zh.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 博客表 前端控制器
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogTagService blogTagService;

    @Autowired
    private TagsService tagsService;

    @PutMapping("/updateBlog")
    public String updateBlog(
            String id,
            String userId,
            String title,
            String summary,
            String image,
            String contentMd,
            String contentHtml,
            String sortId,
            String tags,
            Integer level,
            Boolean status,
            Boolean original,
            Boolean startComment,
            String articlesPart){

        System.out.println("html----"+contentHtml);

        QueryWrapper<BlogTag> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id",id);

        boolean remove = blogTagService.remove(wrapper);
        if (!remove){
            return ResultVO.failure(400,"标签删除失败");
        }

        List<String> list = Arrays.asList(tags.split(","));

        for (String tag : list) {
            BlogTag blogTag = new BlogTag();
            blogTag.setBlogId(id);
            blogTag.setTagId(tag);

            boolean b = blogTagService.save(blogTag);
            if (!b){
                return ResultVO.failure(400,"博客标签修改失败");
            }
        }

        Blog blog = new Blog();
        blog.setId(id);
        blog.setUserId(userId);
        blog.setTitle(title);
        blog.setSummary(summary);
        blog.setImage(image);
        blog.setContentHtml(contentHtml);
        blog.setContentMd(contentMd);
        blog.setSortId(sortId);
        blog.setBlogLevel(level);
        blog.setBlogStatus(status);
        blog.setIsOriginal(original);
        blog.setStartComment(startComment);
        blog.setArticlesPart(articlesPart);

        boolean b = blogService.updateById(blog);

        if (!b){
            return ResultVO.failure(400,"博客修改失败");
        }

        return ResultVO.ok("修改成功");
    }

    @DeleteMapping("/deleteBlog")
    public String deleteBlog(String id){

        QueryWrapper<BlogTag> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id",id);

        boolean remove = blogTagService.remove(wrapper);
        if (!remove){
            return ResultVO.failure(400,"标签删除失败");
        }
        boolean b = blogService.removeById(id);
        if (!b){
            return ResultVO.failure(400,"删除失败");
        }
        return ResultVO.ok("删除成功");
    }

    @GetMapping("/getBlog")
    public String getBlog(
            String keyword,
            String sortId,
            String tagId,
            Integer level,
            Boolean status,
            Boolean original,
            Integer currentPage,
            Integer pageSize){

        IPage<Blog> page = blogService.getBlog(keyword, sortId, tagId, level, status, original, currentPage, pageSize);

        for (Blog blog : page.getRecords()) {
            List<Tags> tags = tagsService.findByBlogId(blog.getId());
            blog.setTags(tags);
        }
        return ResultVO.ok(page);
    }

    @PostMapping("/addBlog")
    public String addBlog(
            String userId,
            String title,
            String summary,
            String image,
            String contentMd,
            String contentHtml,
            String sortId,
            String tags,
            Integer level,
            Boolean status,
            Boolean original,
            Boolean startComment,
            String articlesPart){

        Blog blog = new Blog();
        blog.setUserId(userId);
        blog.setTitle(title);
        blog.setSummary(summary);
        blog.setImage(image);
        blog.setContentHtml(contentHtml);
        blog.setContentMd(contentMd);
        blog.setSortId(sortId);
        blog.setBlogLevel(level);
        blog.setBlogStatus(status);
        blog.setIsOriginal(original);
        blog.setStartComment(startComment);
        blog.setArticlesPart(articlesPart);

        boolean save = blogService.save(blog);

        if (!save){
            return ResultVO.failure(400,"博客添加失败");
        }

        List<String> list = Arrays.asList(tags.split(","));

        for (String tag : list) {
            BlogTag blogTag = new BlogTag();
            blogTag.setBlogId(blog.getId());
            blogTag.setTagId(tag);

            boolean b = blogTagService.save(blogTag);
            if (!b){
                return ResultVO.failure(400,"博客标签添加失败");
            }
        }

        return ResultVO.ok("添加成功");
    }

}

