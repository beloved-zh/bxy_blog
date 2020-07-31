package com.zh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.pojo.Blog;
import com.zh.mapper.BlogMapper;
import com.zh.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 博客表 服务实现类
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
@SuppressWarnings("ALL")
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public IPage<Blog> getBlog(String keyword, String sortId, String tagId, Integer level, Boolean status, Boolean original, Integer currentPage, Integer pageSize) {

        Page<Blog> page = new Page<>(currentPage,pageSize);

        QueryWrapper<Object> wrapper = new QueryWrapper<>();
        wrapper.like("b.title",keyword);
        if (!StringUtils.isEmpty(sortId)){
            wrapper.eq("b.sort_id",sortId);
        }
        if (!StringUtils.isEmpty(tagId)){
            wrapper.eq("t.id",tagId);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("blog_level",level);
        }
        if (!StringUtils.isEmpty(status)){
            wrapper.eq("blog_status",status ? 1 : 0);
        }
        if (!StringUtils.isEmpty(original)){
            wrapper.eq("is_original",original ? 1 : 0);
        }

        IPage<Blog> iPage = blogMapper.getBlog(page, wrapper);

        return iPage;
    }

    @Override
    public List<Map<String, Object>> getBlogCountByLevel() {

        List<Map<String, Object>> list = blogMapper.getBlogCountByLevel();

        for (Map<String, Object> map : list) {
            Integer id = (Integer) map.get("id");
            switch (id){
                case 1 :
                    map.put("name", "一级推荐");
                    break;
                case 2 :
                    map.put("name", "二级推荐");
                    break;
                case 3 :
                    map.put("name", "三级推荐");
                    break;
                default :
                    map.put("name", "正常");
            }
        }

        return list;
    }
}
