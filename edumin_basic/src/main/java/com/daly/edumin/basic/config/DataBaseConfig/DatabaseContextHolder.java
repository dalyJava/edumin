package com.daly.edumin.basic.config.DataBaseConfig;

import cn.hutool.core.util.EnumUtil;

import java.util.List;

/**
 * 作用：
 * 1、保存一个线程安全的DatabaseType容器
 */
public class DatabaseContextHolder {
    private static final ThreadLocal<DatabaseTypeEunm> contextHolder = new ThreadLocal<>();
    public static List<String> dataSourceIds = EnumUtil.getNames(DatabaseTypeEunm.class);

    public static void setDatabaseType(DatabaseTypeEunm type){
        contextHolder.set(type);
    }

    public static DatabaseTypeEunm getDatabaseType(){
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

    /**
     * 判断指定DataSrouce当前是否存在
     *
     * @param dataSourceId
     * @return
     */
    public static boolean containsDataSource(String dataSourceId){
        return dataSourceIds.contains(dataSourceId);
    }
}
