package com.jt.www.controller.userInfo;


import com.jt.www.model.entity.app.AppUserEntity;
import com.jt.www.model.param.app.AddVersionParam;
import com.jt.www.model.param.app.LoginUserParam;
import com.jt.www.model.reps.GenericDataResponse;
import com.jt.www.model.reps.GenericResponse;
import com.jt.www.service.local.app.AppUserService;
import com.jt.www.service.local.app.AppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping(value = "user")
@Api(value = "App版本", tags = {"AppVersion-api"}, description = "App版本控制")
public class UserInfoController {
    @Resource
    private AppUserService appUserService;

    @ApiOperation(value = "App版本创建接口", notes = "App版本创建", httpMethod = "POST")
    @PostMapping(value = "/addAppVersion", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<GenericResponse> addAppVersion(@RequestBody AppUserEntity appUser) {
//        appVersionService.addAppVersion(addVersionParam);
        appUserService.addAppVersion(appUser);
        return GenericResponse.okWithMsg("ok");
    }


    @ApiOperation(value = "登陆", notes = "登陆", httpMethod = "POST")
    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<GenericDataResponse<AppUserEntity>> loginUser(@RequestBody LoginUserParam loginUserParam) {
        //AppUserEntity 定义的入参类
        //appUser 传进来的参数

        AppUserEntity appUserEntity = appUserService.loginUser(loginUserParam);
        return GenericDataResponse.okWithData(appUserEntity);
    }

}
