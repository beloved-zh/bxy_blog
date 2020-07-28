package com.zh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 请求地址表
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="RequestApi对象", description="请求地址表")
public class RequestApi implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "请求地址")
    private String url;

    @ApiModelProperty(value = "请求方式 ALL表示都可以")
    private String method;

    @ApiModelProperty(value = "菜单分类 0后台 1前台")
    private Boolean apiType;

    @ApiModelProperty(value = "介绍")
    private String summary;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "父id")
    private String requestId;

    // 存取子路由，方便使用，不是数据库字段
    @TableField(exist = false)
    private List<RequestApi> children;

    @TableField(exist = false)
    private List<Role> roles;
}
