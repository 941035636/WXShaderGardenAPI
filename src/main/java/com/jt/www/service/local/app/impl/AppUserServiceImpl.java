package com.jt.www.service.local.app.impl;

import com.jt.www.dao.mapper.app.AppUserMapper;
import com.jt.www.exception.BizException;
import com.jt.www.model.entity.app.AppUserEntity;
import com.jt.www.model.param.app.LoginUserParam;
import com.jt.www.service.local.app.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

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
//        insert 新增
        appUserMapper.insert(appUserEntity);
        return null;
    }

    public AppUserEntity loginUser(LoginUserParam loginUserParam) {
        if(loginUserParam.getUsername() == null || loginUserParam.getUsername().isEmpty()){
            throw new BizException("请输入用户名");
        }else if(loginUserParam.getPasswordCode() == null || loginUserParam.getPasswordCode().isEmpty()){
            throw new BizException("请输入用户密码");
        }
        Example example = new Example(AppUserEntity.class);
        example.createCriteria().andEqualTo("username",loginUserParam.getUsername()).andEqualTo("passwordCode",loginUserParam.getPasswordCode());
        //selectByExample查询
        List<AppUserEntity> appUserEntities = appUserMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(appUserEntities)){
            throw new BizException("用户不存在");
        }
        return appUserEntities.get(0);
    }
}
