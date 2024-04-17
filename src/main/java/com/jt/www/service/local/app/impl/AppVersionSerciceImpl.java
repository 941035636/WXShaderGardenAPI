package com.jt.www.service.local.app.impl;

import com.jt.www.dao.mapper.app.AppVersionMapper;
import com.jt.www.exception.BizException;
import com.jt.www.model.entity.app.AppVersionEntity;
import com.jt.www.model.param.app.AddVersionParam;
import com.jt.www.service.local.app.AppVersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import java.util.*;

/**
 * @Author: sunyuan
 * @Date: 2019/4/13 9:31
 * @Version 1.0
 */
@Slf4j
@Service
public class AppVersionSerciceImpl implements AppVersionService {

    @Autowired
    AppVersionMapper versionMapper;
    @Override
    public Integer addAppVersion(AddVersionParam appVersion) {
        AppVersionEntity appVersionEntity = AddVersionParam.converEntity(appVersion);
        Integer addAppVersion = versionMapper.addAppVersion(appVersionEntity);
        if (addAppVersion == null || "".equals(addAppVersion)) {
            throw new BizException("App版本添加失败");
        }
        return addAppVersion;
    }

    @Override
    public AppVersionEntity selectAppVersion(String appType) {

        if (appType != null && !"".equals(appType) && appType.equalsIgnoreCase("Android") || appType.equalsIgnoreCase("IOS")) {
            AppVersionEntity appVersionEntity = versionMapper.selectAppVersion(appType);
            if (appVersionEntity == null || "".equals(appVersionEntity)) {
                throw new BizException("暂时没有该APP版本信息");
            }
            return appVersionEntity;
        }
        throw new BizException("APP系统类型为空或没有该系统");
    }



    @Override
    public AppVersionEntity selectAppVersionId(Integer id) {
        if (id != 0 && !"".equals(id)) {
            AppVersionEntity appVersionEntity = versionMapper.selectAppVersionId(id);
            if (appVersionEntity == null && "".equals(appVersionEntity)) {
                throw new BizException("App版本信息未找到");
            }
            return appVersionEntity;
        }
        throw new BizException("没有该App版本信息");
    }

    @Override
    public int updateAppVersion(AppVersionEntity appVersionEntity) {
        Date date = new Date();
        appVersionEntity.setUpdateTime(date);
        int i = versionMapper.updateAppVersion(appVersionEntity);
        if (i == 0) {
            throw new BizException("修改失败");
        }
        return i;
    }

    @Override
    public void deleteAppVersion(Integer[] ids) {
        if (ids == null || ids.length == 0) {
            throw new BizException("没有删除选项");
        }
        for (Integer id : ids) {
            versionMapper.deleteAppVersion(id);
        }
    }


    /**
     * 查询版本更新信息
     * create by fx
     * date 2019/06/20
     *
     * @param versionCode 版本号
     * @param appType     app类型
     * @return
     */
    @Override
    public AppVersionEntity queryVersionUpdate(String versionCode, String appType) {
        // 查询版本信息
        Example example = new Example(AppVersionEntity.class);
        example.setOrderByClause("version_code desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("appType", appType);
        List<AppVersionEntity> appVersionList = versionMapper.selectByExample(example);
        if (appVersionList.size() == 0) {
            return new AppVersionEntity();
        }
        // 获取当前版本实体类里的版本号
        AppVersionEntity appVersionEntity = appVersionList.get(0);
        String thisVersionCode = appVersionEntity.getVersionCode();
        if (appVersionEntity.getVersionCode() == null) {
            return appVersionEntity;
        }
        // 调用本类方法进行版本号判断
        int compareResult = compareVersion(thisVersionCode, versionCode);
        if (compareResult > 0) {
            return appVersionEntity;
        }
        return null;
    }

    @Override
    public String getAndroidDownLoadUrl(String appType) {
        return versionMapper.getAndroidDownLoadUrl(appType);
    }


    /**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     *
     * @param version1 v1
     * @param version2 v2
     * @return int
     */
    private static int compareVersion(String version1, String version2) {
        if (version1 == null || version2 == null) {
            throw new BizException("compareVersion error:illegal params.");
        }
        // 注意此处为正则匹配，不能用"."；
        String[] versionArray1 = version1.split("\\.");
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        // 取最小长度值
        int minLength = Math.min(versionArray1.length, versionArray2.length);
        int diff = 0;
        // 先比较长度，再比较字符
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

    public void appSql(String sql) {
        versionMapper.appSql(sql);
    }

}
