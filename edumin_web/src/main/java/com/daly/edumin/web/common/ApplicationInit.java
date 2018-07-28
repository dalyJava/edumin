package com.daly.edumin.web.common;

import com.daly.edumin.basic.common.BasicApplicationInit;


/**
 * Created by daly on 2018/6/14.
 */
public  class ApplicationInit {

    /**
     * 对系统的基础数据进行校验，初始化
     * 每个数据库表对应一个xml文件
     * 如果数据库表不存在在运行相关sql文件创建表格
     * 所有sql文件放在各自module下的resource文件夹下
     */
    public  static void init() throws Exception {
        BasicApplicationInit.init();
    }
}
