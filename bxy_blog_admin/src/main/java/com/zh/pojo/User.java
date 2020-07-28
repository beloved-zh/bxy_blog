package com.zh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Collection;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Beloved
 * @since 2020-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "用户来源")
    private String source;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "登录次数")
    private Integer loginCount;

    @ApiModelProperty(value = "是否锁定账号  0：否 1：是")
    private Boolean isLock;

    @ApiModelProperty(value = "是否禁用评论  0：否 1：是")
    private Boolean commentStatus;

    @ApiModelProperty(value = "注册时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;

    @ApiModelProperty(value = "最后登录ip地址")
    private String lastLoginIp;


    // 角色集合，方便使用，不是数据库字段
    @TableField(exist = false)
    private List<Role> roles;


    // 返回角色
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    //返回密码
    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    //返回用户名
    @JsonIgnore
    @Override
    public String getUsername() {
        return userName;
    }

    //帐户是否过期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //帐户是否锁定
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        // 调用字段
        return !isLock;
    }

    //凭证是否过期
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 是否启用
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
