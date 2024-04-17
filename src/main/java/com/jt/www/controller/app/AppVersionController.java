package com.jt.www.controller.app;


import com.jt.www.model.entity.app.AppVersionEntity;
import com.jt.www.model.param.app.AddVersionParam;
import com.jt.www.model.reps.GenericDataResponse;
import com.jt.www.model.reps.GenericResponse;
import com.jt.www.service.local.app.AppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * @Author: sunyuan
 * @Date: 2019/4/13 10:13
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "app")
@Api(value = "App版本", tags = {"AppVersion-api"}, description = "App版本控制")
public class AppVersionController {

    @Resource
    private AppVersionService appVersionService;

    @ApiOperation(value = "App版本创建接口", notes = "App版本创建", httpMethod = "POST")
    @PostMapping(value = "/addAppVersion", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<GenericResponse> addAppVersion(@RequestBody AddVersionParam addVersionParam) {
        appVersionService.addAppVersion(addVersionParam);
        return GenericResponse.ok();
    }

    @ApiOperation(value = "按App类型查询最新版本", notes = "查询最新版本", httpMethod = "GET")
    @GetMapping(value = "/selsctAppVersion", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<GenericDataResponse<AppVersionEntity>> selectAppVersion(String appType) {
        AppVersionEntity appVersionEntity = appVersionService.selectAppVersion(appType);
        return GenericDataResponse.okWithData(appVersionEntity);

    }


    @ApiOperation(value = "根据App版本Id查询版本信息", notes = "Id查询版本", httpMethod = "GET")
    @GetMapping(value = "/selectVersionId", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<GenericDataResponse<AppVersionEntity>> selectAppVersionId(Integer id) {
        AppVersionEntity appVersionEntity = appVersionService.selectAppVersionId(id);
        return GenericDataResponse.okWithData(appVersionEntity);
    }

    @ApiOperation(value = "修改App版本信息", notes = "App版本信息修改", httpMethod = "POST")
    @PostMapping(value = "/updateAppVersion", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<GenericResponse> updateAppVersion(
            @ApiParam(name = "App版本参数", value = "App版本修改参数、App版本修改json入参", required = true) @RequestBody AppVersionEntity VersionEntity) {
        appVersionService.updateAppVersion(VersionEntity);
        return GenericResponse.ok();
    }

    @ApiOperation(value = "删除App版本信息", notes = "App版本信息删除", httpMethod = "DELETE")
    @DeleteMapping(value = "/deleteAppVersion", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<GenericResponse> deleteAppVersion(Integer[] id) {
        appVersionService.deleteAppVersion(id);
        return GenericResponse.ok();
    }


    @ApiOperation(value = "查询App版本更新(获取App更新专用)", notes = "查询版本更新请求", httpMethod = "GET")
    @GetMapping(value = "/queryVersionUpdate", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<?> queryVersionUpdate(@RequestParam(value = "versionCode") String versionCode,
                                                @RequestParam(value = "appType") String appType) {
        if (versionCode == null || appType == null) {
            return GenericResponse.ng("版本号或版本类型入参不能为空!");
        }
        AppVersionEntity appVersionEntity = appVersionService.queryVersionUpdate(versionCode, appType);
        if (appVersionEntity == null || appVersionEntity.getDownloadUrl() == null) {
            return GenericResponse.ok();
        }
        return GenericDataResponse.okWithData(appVersionEntity);
    }

    @ApiOperation(value = "查询App下载地址)", notes = "查询App下载地址", httpMethod = "GET")
    @GetMapping(value = "/getAndroidDownLoadUrl", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<GenericDataResponse<String>> getAndroidDownLoadUrl(@RequestParam(value = "appType") String appType) {
        String result = appVersionService.getAndroidDownLoadUrl(appType);
        if (StringUtils.isBlank(result)) {
            GenericResponse.ng("查询失败");
        }
        return GenericDataResponse.okWithData(result);
    }

}
