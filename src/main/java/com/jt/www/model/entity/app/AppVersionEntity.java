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

/**
 * @ClassName AppVersionEntity
 * @Description //TODO
 * @Author sunyuan
 * @Date 2019/4/13
 * @Version 1.0
 **/

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "app_version")
@ApiModel
public class AppVersionEntity {


    @ApiModelProperty(value = "ID")
    private Integer Id;

    @ApiModelProperty(value = "版本号")
    private String versionCode;

    @ApiModelProperty(value = "下载地址")
    private String downloadUrl;

    @ApiModelProperty(value = "更新日志")
    private String updateLog;

    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "是否强制更新")
    private String isInstall;

    @ApiModelProperty(value = "app类型(ios,Android)")
    private String appType;

    @ApiModelProperty(value = "文件大小")
    private String fileSize;

}
