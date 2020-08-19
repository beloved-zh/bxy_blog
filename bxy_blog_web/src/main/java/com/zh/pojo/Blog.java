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
 * 博客表
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Blog对象", description="博客表")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "博客id")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章简介")
    private String summary;

    @ApiModelProperty(value = "文章标题图片")
    private String image;

    @ApiModelProperty(value = "markdown格式内容")
    private String contentMd;

    @ApiModelProperty(value = "html格式内容")
    private String contentHtml;

    @ApiModelProperty(value = "分类id")
    private String sortId;

    @ApiModelProperty(value = "文章点击数")
    private Integer clickCount;

    @ApiModelProperty(value = "作者")
    private String userId;

    @ApiModelProperty(value = "是否置顶 0：否 1：是")
    private Boolean isTop;

    @ApiModelProperty(value = "是否原创 0：否 1：是")
    private Boolean isOriginal;

    @ApiModelProperty(value = "转载地址")
    private String articlesPart;

    @ApiModelProperty(value = "推荐等级 0默认")
    private Integer blogLevel;

    @ApiModelProperty(value = "状态 0：未发布  1：已发布")
    private Boolean blogStatus;

    @ApiModelProperty(value = "是否开启评论 0：否 1是")
    private Boolean startComment;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    // 非数据库字段
    @TableField(exist = false)
    private List<Tags> tags;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private Sort sort;

    // 评论数
    @TableField(exist = false)
    private Integer commentCount;

    // 点赞数
    @TableField(exist = false)
    private Integer fabulousCount;
}
