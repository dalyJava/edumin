package com.daly.edumin.basic.factory.impl;


import com.daly.edumin.basic.factory.EduFactory;

import java.util.LinkedHashMap;

/**
 * Created by daly on 2018-2-9.
 */
public class EduFactoryImpl implements EduFactory {
    /**
     * 创建一个LinkedHashMap<>，
     * apache shiro 权限管理需要使用到这个
     */
    @Override
    public LinkedHashMap<String, String> createdFilterChainDefinitionMap() {
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //不需要验证
        //filterChainDefinitionMap.put("/**", "anon"); //关闭权限方便调试
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/userLogin", "anon");
        filterChainDefinitionMap.put("/resource/**", "anon");
        filterChainDefinitionMap.put("/unauth", "anon");
        filterChainDefinitionMap.put("/login.html", "anon");
        filterChainDefinitionMap.put("/pages/common/403.html", "anon");
        filterChainDefinitionMap.put("/404.html", "anon");
        //需要验证
        filterChainDefinitionMap.put("/**", "authc");
        return filterChainDefinitionMap;
    }
}
