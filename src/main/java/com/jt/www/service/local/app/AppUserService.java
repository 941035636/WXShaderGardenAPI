package com.jt.www.service.local.app;

import com.github.pagehelper.PageInfo;
import com.jt.www.model.entity.app.AppUserEntity;
import com.jt.www.model.entity.app.AppVersionEntity;
import com.jt.www.model.param.app.AddVersionParam;
import org.springframework.http.ResponseEntity;

import java.util.Map;


public interface AppUserService {
    Integer addAppVersion(AppUserEntity appVersion);

}
