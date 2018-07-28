package com.daly.edumin.basic.config.shiro;

import cn.hutool.core.util.StrUtil;
import com.daly.edumin.basic.domain.MenuEntity;
import com.daly.edumin.basic.domain.UserEntity;
import com.daly.edumin.basic.service.MenuService;
import com.daly.edumin.basic.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by daly on 2018-2-8.
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    /**
     * 权限验证时调用
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserEntity user = (UserEntity)principalCollection.getPrimaryPrincipal();
        List<String> permsList = null;
        //系统管理员，拥有最高权限
        if(ShiroUtil.hasRoleAdmin()){
            List<MenuEntity> menuList = menuService.getMenuAllList();
            permsList = new ArrayList<>(menuList.size());
            for(MenuEntity menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = menuService.getAllPerms(user.getRoleIdList());
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StrUtil.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        //将角色加入info
        Set<String> rolesSet = new HashSet<>();
        List<String> roleNameList = user.getRoleNameList();
        for (String temp: roleNameList){
            rolesSet.add(temp);
        }
        info.setRoles(rolesSet);
        return info;
    }

    /**
     * 登录时调用
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        //查询用户信息
        UserEntity userEntity = userService.queryByUserName(username);

        //账号不存在
        if(userEntity == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }

        //密码错误
        if(!password.equals(userEntity.getPassword())) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }

        //账号锁定
        if(userEntity.getStatus() == 0){
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userEntity, password, getName());
        return info;
    }
}
