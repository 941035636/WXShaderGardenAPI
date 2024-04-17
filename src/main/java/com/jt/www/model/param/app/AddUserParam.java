package com.jt.www.model.param.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jt.www.model.entity.app.AppVersionEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * @Author: sunyuan
 * @Date: 2019/4/13 9:42
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel

//@Table(name = "app_version")

public class AddUserParam {

    //app版本
    @Min(value = 1, message = "主键自增")
    @ApiModelProperty(value = "app版本id",  required = true)
    private Integer Id;


    //版本号
    @ApiModelProperty(value = "版本号", required = true)
    private String VersionCode;


    //下载地址
    @ApiModelProperty(value = "下载地址", required = true)
    private String DownloadUrl;


    //更新日志  update_log
    @ApiModelProperty(value = "更新日志", required = true)
    private String UpdateLog;


    //更新时间   update_time
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间", hidden = true)
    private Date UpdateTime;


    // 是否强制下载 Y是  N否 is_install
    @ApiModelProperty(value = "是否强制下载 Y是 N否", required = true)
    private String IsInstall;


    // Android IOS   app_type
    @ApiModelProperty(value = "Android / IOS", required = true)
    private String AppType;


    // 版本大小
    @ApiModelProperty(value = "App版本大小", required = true)
    private String FileSize;


    public static AppVersionEntity converEntity(AddUserParam VersionParam) {
        AppVersionEntity newAppVersion = new AppVersionEntity();
        Date date = new Date();
        newAppVersion.setId(VersionParam.getId());
        newAppVersion.setVersionCode(VersionParam.getVersionCode());
        newAppVersion.setDownloadUrl(VersionParam.getDownloadUrl());
        newAppVersion.setUpdateLog(VersionParam.getUpdateLog());
        newAppVersion.setUpdateTime(date);
        newAppVersion.setIsInstall(VersionParam.getIsInstall());
        newAppVersion.setAppType(VersionParam.getAppType());
        newAppVersion.setFileSize(VersionParam.getFileSize());
        return  newAppVersion;
    }


}
