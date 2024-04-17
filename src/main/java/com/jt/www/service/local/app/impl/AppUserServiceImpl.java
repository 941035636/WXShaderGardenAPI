package com.jt.www.service.local.app.impl;

import com.jt.www.dao.mapper.app.AppUserMapper;
import com.jt.www.dao.mapper.app.AppVersionMapper;
import com.jt.www.model.entity.app.AppUserEntity;
import com.jt.www.model.param.app.AddVersionParam;
import com.jt.www.service.local.app.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    AppUserMapper appUserMapper;

    @Override
    public Integer addAppVersion(AppUserEntity appUserEntity) {
//        AppUserEntity appUserEntity = new AppUserEntity();
//        appUserEntity.setId(1);
        appUserMapper.insert(appUserEntity);
        return null;
    }
}
