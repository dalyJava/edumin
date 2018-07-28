package com.daly.edumin.file.service.impl;

import com.daly.edumin.basic.config.DefaultValue;
import com.daly.edumin.basic.config.TargetDataSource;
import com.daly.edumin.file.domain.UserFile;
import com.daly.edumin.file.mapper.UserFileMapper;
import com.daly.edumin.file.service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by daly on 2018/7/12.
 */
@Service
public class UserFileServiceImpl implements UserFileService {
    @Autowired
    private UserFileMapper userFileMapper;

    @Override
    @TargetDataSource(name=DefaultValue.DATASOURCE_EDUMIN_FILE)
    public UserFile getFileInfo() {
        UserFile file11 = userFileMapper.getFileInfo();
        return file11;
    }
}
