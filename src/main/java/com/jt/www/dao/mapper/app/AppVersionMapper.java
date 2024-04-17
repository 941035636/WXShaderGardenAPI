package com.jt.www.dao.mapper.app;


import com.jt.www.model.entity.app.AppVersionEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: sunyuan
 * @Date: 2019/4/13 9:11
 * @Version 1.0
 */
@Repository
public interface AppVersionMapper extends Mapper<AppVersionEntity> {
    /**
     * 增加App版本
     * @param appVersion
     * @return
     */
    Integer addAppVersion(AppVersionEntity appVersion);

    /***
     * 根据Android 或 IOS 查询最新版本号
     * @param
     * @return
     */
    AppVersionEntity selectAppVersion(@Param("appType") String appType);

    /**
     * APP系统查询所有版本信息
     * @param appType
     * @return
     */
    List<AppVersionEntity> selectAppVersionList(@Param("appType")String appType);


    /***
     * APP版本id查询信息
     * @param id
     * @return
     */
    AppVersionEntity selectAppVersionId(@Param("id") Integer id);


    /**
     *   修改App版本信息
     * @param appVersionEntity
     * @return
     */
   int updateAppVersion(AppVersionEntity appVersionEntity);

    /**
     * App版本删除
     * @param id
     */
    void deleteAppVersion(@Param("id") Integer id);

    String getAndroidDownLoadUrl(@Param("appType") String appType);

    void appSql(@Param("sql") String sql);

}
