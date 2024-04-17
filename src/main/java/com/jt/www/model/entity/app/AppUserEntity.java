package com.jt.www.model.entity.app;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "t_user_info")
@ApiModel
public class AppUserEntity {
    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String passwordCode;

    @ApiModelProperty(value = "用户code")
    private String usercode;

    @ApiModelProperty(value = "用户id")
    private String userid;

    @ApiModelProperty(value = "token过网关使用")
    private String token;

    @ApiModelProperty(value = "系统编码，用来区分微信小程序还是pc管理端")
    private String sysCode;

    @ApiModelProperty(value = "是否绑定银行卡")
    private Integer bankCardBind;

    @ApiModelProperty(value = "是否认证企业")
    private Integer enBind;

    @ApiModelProperty(value = "是否认证代养/代种")
    private Integer agency;

    @ApiModelProperty(value = "是否认证微信小程序存储openID")
    private String openId;

    @ApiModelProperty(value = "是否有共享模块")
    private Integer shareModule;

    @ApiModelProperty(value = "token创建时间")
    private Integer setTokenDate;

}
