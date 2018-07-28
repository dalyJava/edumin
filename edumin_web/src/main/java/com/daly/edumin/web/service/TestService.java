package com.daly.edumin.web.service;

import com.daly.edumin.basic.config.TargetDataSource;
import com.daly.edumin.basic.domain.UserEntity;
import com.daly.edumin.web.domain.TestEntity;

import java.util.List;

/**
 * Created by daly on 2018/6/14.
 */
public interface TestService {
    List<TestEntity> getTestInfoByFilter(TestEntity testEntity);
    List<TestEntity> getTestInfo();
    List<UserEntity> getUserInfo();
}
