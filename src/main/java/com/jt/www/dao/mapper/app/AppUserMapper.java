package com.jt.www.dao.mapper.app;


import com.jt.www.model.entity.app.AppUserEntity;
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
public interface AppUserMapper extends Mapper<AppUserEntity> {



}
