package com.daly.edumin.basic.factory;

import java.util.LinkedHashMap;

/**
 * 用户自定义系统工厂，
 * Created by daly on 2018-2-9.
 */
public interface EduFactory {
    /**
     *创建一个LinkedHashMap<>，
     * apache shiro 权限管理需要使用到这个
     */
    LinkedHashMap<String, String> createdFilterChainDefinitionMap();
}
