package com.daly.edumin.basic.common;

import com.daly.edumin.basic.util.DbUtils;
import com.daly.edumin.basic.util.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by daly on 2018/6/15.
 */
public class BasicApplicationInit {
    private static Logger LOG = LoggerFactory.getLogger(BasicApplicationInit.class);

    /**
     * 分两部分，①初始化数据库及数据库表
     * ② 更新 数据库表格
     * @throws Exception
     */
    public static void init() throws Exception {
        //①初始化数据库及数据库表 只初始化一次
        String modulesDB = PropertiesUtils.getProperty("edumin.modules");
        List<String> modulesDBList = Arrays.asList(modulesDB.split(","));
        for (String moduleName : modulesDBList){
            DbUtils.DBInit(moduleName);
        }
        //② 更新 数据库表格 每次启动校验更新
        for (String moduleName : modulesDBList){
            DbUtils.updateDBTable(moduleName);
        }
        LOG.info("======i'm info log");
        LOG.error("======i'm info log");
    }

}
