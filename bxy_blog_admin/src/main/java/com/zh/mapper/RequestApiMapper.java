package com.zh.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.pojo.Menu;
import com.zh.pojo.RequestApi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 请求地址表 Mapper 接口
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
public interface RequestApiMapper extends BaseMapper<RequestApi> {

    List<RequestApi> getRequestApiByUserName(@Param("username") String username);

}
