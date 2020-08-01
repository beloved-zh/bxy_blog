package com.zh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.pojo.Blog;
import com.zh.mapper.BlogMapper;
import com.zh.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

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

    @Override
    public Map<String, Object> getBlogContributeCount() {
        // 获取当前时间
        String endTime = DateUtil.getNowTime();
        // 获取365天前的日期
        Date temp = DateUtil.getDate(endTime, -365);
        String startTime = DateUtil.dateTimeToStr(temp);

        List<String> contributeDateList = new ArrayList<>();
        contributeDateList.add(startTime);
        contributeDateList.add(endTime);

        List<Map<String, Object>> listMapper = blogMapper.getBlogContributeCount(startTime, endTime);

        // 获取某个时间段内所有日期
        List<String> dateList = DateUtil.getDayBetweenDates(startTime, endTime);


        Map<String, Object> dateMap = new HashMap<>();
        for (Map<String, Object> map : listMapper) {
            dateMap.put(map.get("date").toString(),map.get("count"));
        }

        List<List<Object>> resultList = new ArrayList<>();
        for (String item : dateList) {
            Integer  count = 0;
            if (dateMap.get(item) != null){
                count = Integer.valueOf(dateMap.get(item).toString());
            }

            List<Object> objects = new ArrayList<>();
            objects.add(item);
            objects.add(count);
            resultList.add(objects);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("contributeDate", contributeDateList);
        resultMap.put("blogContributeCount", resultList);
        return resultMap;
    }
}
