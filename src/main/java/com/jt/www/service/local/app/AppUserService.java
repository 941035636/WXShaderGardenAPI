package com.jt.www.service.local.app;

import com.jt.www.model.entity.app.AppUserEntity;
import com.jt.www.model.param.app.LoginUserParam;

import java.util.List;


public interface AppUserService {
    Integer addAppVersion(AppUserEntity appVersion);

    AppUserEntity loginUser(LoginUserParam loginUserParam);

}
