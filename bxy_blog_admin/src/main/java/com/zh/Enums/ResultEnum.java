package com.zh.Enums;

import lombok.Getter;

/**
 * @author Beloved
 * @date 2020/6/22 15:03
 * @description: 返回的错误码枚举类
 */
@Getter
public enum ResultEnum {

    SUCCESS(200,"成功"),
    FAILURE(400,"失败"),
    USER_NEED_AUTHORITIES(401,"用户未登录"),
    USER_LOGIN_FAILED(402,"用户账号或密码错误"),
    USER_LOGIN_SUCCESS(200,"用户登录成功"),
    USER_NO_ACCESS(403,"用户无权访问"),
    USER_LOGOUT_SUCCESS(200,"用户登出成功"),
    NOT_FOUND( 404, "接口不存在"),
    TOKEN_OVER(405,"token已过期"),
    TOKEN_BAD(406,"token格式错误"),
    INTERNAL_SERVER_ERROR( 500, "服务器内部错误");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过code返回枚举
     */
    public static ResultEnum parse(int code){
        ResultEnum[] values = values();
        for (ResultEnum value : values) {
            if(value.getCode() == code){
                return value;
            }
        }
        throw  new RuntimeException("Unknown code of ResultEnum");
    }

}
