package com.zh.mapper;

import com.zh.pojo.Discuss;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
public interface DiscussMapper extends BaseMapper<Discuss> {

    List<Discuss> getDiscussFather(String blogId);

    List<Discuss> getDiscussSon(String id);

}
