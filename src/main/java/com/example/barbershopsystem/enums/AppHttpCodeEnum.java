package com.example.barbershopsystem.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
     PHONENUMBER_EXIST(502,"手机号已存在"), EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    TOKEN_ERROR(506,"token非法"),
    ImG_ERROR(507,"图片上传失败"),
    EMPTY_ERROR(508,"请选择图片上传"),

    REGISTER_ERROR(509,"注册失败"),
    NOT_FINDIMG_ERROR(511,"未查询到轮播图数据"),
    UPDATE_ERROR(510,"修改失败"),
    ADD_ERROR(512,"添加失败"),
    NOT_FIND_ERROR(513,"未查询到数据"),
    DELETE_ERROR(514,"删除失败"),
    NOT_FINDUSER_ERROR(515,"当前用户不存在"),
    EXIST_APPOINTMENT(516,"有未完成的预约记录"),

   ;

    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}