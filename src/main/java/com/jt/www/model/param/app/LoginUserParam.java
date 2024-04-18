package com.jt.www.model.param.app;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginUserParam {

    /**
     * 用户名
     */
    @NotNull
    private String username;

    /**
     * 用户密码
     */
    @NotNull
    private String passwordCode;

    /**
     * 图片验证码
     */
    private String imgCode;

    /**
     * 图片验证码key
     */
    private String imgKey;
}
