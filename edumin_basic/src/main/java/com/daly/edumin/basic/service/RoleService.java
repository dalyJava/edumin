package com.daly.edumin.basic.service;


import com.daly.edumin.basic.domain.RoleEntity;

import java.util.List;

/**
 * Created by daly on 2018-2-26.
 */
public interface RoleService {
    /**
     * 根据选中的Ids批量删除
     * @param roleIdsArr
     * @return
     */
    Integer delRoleByIds(Integer[] roleIdsArr);

    /**
     * 添加或保存角色
     * @param roleEntity
     * @return
     */
    String saveOrUpdateRole(RoleEntity roleEntity);

    /**
     * 获取所有的roleList
     * @return
     */
    List<RoleEntity> getRoleList();

    /**
     * 根据roleFilter  查询roleList
     * @param roleFilter
     * @return
     */
    List<RoleEntity> getRoleListByFilter(RoleEntity roleFilter);
}
