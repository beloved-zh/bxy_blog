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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Menu对象", description="菜单表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "名称")
    private String menuTitle;

    @ApiModelProperty(value = "图标")
    private String menuIcon;

    @ApiModelProperty(value = "地址")
    private String menuUrl;

    @ApiModelProperty(value = "路由组件")
    private String component;

    @ApiModelProperty(value = "父id")
    private String menuId;

    @ApiModelProperty(value = "转发地址")
    private String redirect;

    @ApiModelProperty(value = "排序 值越小越靠前")
    private Integer sort;

    @ApiModelProperty(value = "是否显示在侧边栏 0显示(false) 1不显示")
    private Boolean isShow;

    @ApiModelProperty(value = "是否固定在tagview 0不固定 1固定")
    private Boolean affix;

    @ApiModelProperty(value = "菜单分类 0后台 1前台")
    private Boolean menuType;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    // 存取角色信息，方便使用，不是数据库字段
    @TableField(exist = false)
    private List<Role> roles;

    // 存取子路由，方便使用，不是数据库字段
    @TableField(exist = false)
    private List<Menu> children;

}
