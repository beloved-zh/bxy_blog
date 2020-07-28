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
 * 系统配置表
 * </p>
 *
 * @author Beloved
 * @since 2020-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SystemConfig对象", description="系统配置表")
public class SystemConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "七牛云公钥")
    private String qiNiuAccessKey;

    @ApiModelProperty(value = "七牛云私钥")
    private String qiNiuSecretKey;

    @ApiModelProperty(value = "七牛云上传空间")
    private String qiNiuBucket;

    @ApiModelProperty(value = "七牛云域名前缀")
    private String qiNiuPictureBaseUrl;

    @ApiModelProperty(value = "邮箱账号")
    private String email;

    @ApiModelProperty(value = "邮箱发件人用户名")
    private String emailUserName;

    @ApiModelProperty(value = "邮箱密码")
    private String emailPassword;

    @ApiModelProperty(value = "smtp地址")
    private String smtpAddress;

    @ApiModelProperty(value = "smtp端口")
    private String smtpPort;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
