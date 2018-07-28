package com.daly.edumin.web.mapper;

import com.daly.edumin.basic.config.TargetDataSource;
import com.daly.edumin.web.domain.TestEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by daly on 2018/7/11.
 */
@Mapper
public interface TestMapper {
    List<TestEntity> getTestInfoByFilter(TestEntity testEntity);
    List<TestEntity> getTestInfo();
}
