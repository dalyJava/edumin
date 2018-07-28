package com.daly.edumin.basic.config.shiro;

import cn.hutool.core.util.StrUtil;
import com.daly.edumin.basic.domain.UserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * Created by daly on 2018-3-6.
 */
public class ShiroUtil {

    /**
     * 是否拥有该权限
     * @param permission  权限标识
     * @return   true：是     false：否
     */
    public static boolean hasPermission(String permission) {
        Subject subject = SecurityUtils.getSubject();
        return subject != null && subject.isPermitted(permission);
    }

    /**
     * 判断当前用户是否是管理员
     * @return
     */
    public static boolean hasRoleAdmin(){
        UserEntity userEntity = (UserEntity)SecurityUtils.getSubject().getPrincipal();
        List<String> roleNameList = userEntity.getRoleNameList();
        for(String temp : roleNameList){
            if(StrUtil.equals(temp,"admin"))
                return true;
        }
        return false;
    }

}
