package com.daly.edumin.file.mapper;

import com.daly.edumin.file.domain.UserFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * Created by daly on 2018/7/12.
 */
@Mapper
@Component(value = "userFileMapper")
public interface UserFileMapper {
    UserFile getFileInfo();
}
