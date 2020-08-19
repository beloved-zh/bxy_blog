package com.zh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.pojo.Fabulous;
import com.zh.pojo.SystemConfig;

import java.util.List;

/**
 * <p>
 * 点赞表 Mapper 接口
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
public interface FabulousMapper extends BaseMapper<Fabulous> {

    List<Fabulous> getFabulousByUser(String userId);

}
