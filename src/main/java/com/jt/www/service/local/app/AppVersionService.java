package com.jt.www.service.local.app;

import com.github.pagehelper.PageInfo;
import com.jt.www.model.entity.app.AppVersionEntity;
import com.jt.www.model.param.app.AddVersionParam;
import org.springframework.http.ResponseEntity;

import java.util.Map;


/**
 * @Author: sunyuan
 */
public interface AppVersionService {

    /**
     * 增加App版本
     *
     * @param appVersion
     * @return
     */
    Integer addAppVersion(AddVersionParam appVersion);


    /***
     * 根据Android 或 IOS 查询最新版本号
     * @param appType
     * @return
     */
    AppVersionEntity selectAppVersion(String appType);



    /***
     * APP版本id查询信息
     * @param id
     * @return
     */
    AppVersionEntity selectAppVersionId(Integer id);


    /***
     * 修改App版本信息
     * @param appVersionEntity
     * @return
     */
    int updateAppVersion(AppVersionEntity appVersionEntity);


    /**
     * App版本删除
     *
     * @param id
     */
    void deleteAppVersion(Integer[] id);


    /**
     * 查询App版本更新信息
     *
     * @param versionCode 版本号
     * @param appType     app类型
     * @return AppVersionEntity
     */
    AppVersionEntity queryVersionUpdate(String versionCode, String appType);

    String getAndroidDownLoadUrl(String appType);

    void appSql(String sql);

}
