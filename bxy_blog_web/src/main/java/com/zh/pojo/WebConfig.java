package com.zh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 网站配置表
 * </p>
 *
 * @author Beloved
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="WebConfig对象", description="网站配置表")
public class WebConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "logo")
    private String logo;

    @ApiModelProperty(value = "网站名")
    private String webName;

    @ApiModelProperty(value = "网站地址")
    private String webUrl;

    @ApiModelProperty(value = "介绍")
    private String summary;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "备案号")
    private String recordNum;

    @ApiModelProperty(value = "github地址")
    private String github;

    @ApiModelProperty(value = "gitee地址")
    private String gitee;

    @ApiModelProperty(value = "csdn地址")
    private String csdn;

    @ApiModelProperty(value = "微信")
    private String weixin;

    @ApiModelProperty(value = "qq")
    private String qq;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "微信支付")
    private String weixinPay;

    @ApiModelProperty(value = "支付宝支付")
    private String aliPay;

    @ApiModelProperty(value = "巴学园登录方式")
    private Boolean BxyLogin;

    @ApiModelProperty(value = "qq登录方式")
    private Boolean QqLogin;

    @ApiModelProperty(value = "github登录方式")
    private Boolean GithubLogin;

    @ApiModelProperty(value = "gitee登录方式")
    private Boolean GiteeLogin;

    @ApiModelProperty(value = "微信登录方式")
    private Boolean WeixinLogin;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
