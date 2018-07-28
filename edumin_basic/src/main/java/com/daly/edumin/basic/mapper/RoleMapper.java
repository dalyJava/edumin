package com.daly.edumin.basic.mapper;


import com.daly.edumin.basic.domain.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by daly on 2018-2-25.
 */
@Mapper
public interface RoleMapper {
    /**
     *根据roleId删除role
     * @param roleIdsArr
     * @return
     */
    Integer delRoleByRoleIds(Integer[] roleIdsArr);

    /**
     * 保存角色实体
     * @param roleEntity
     * @return
     */
    Integer saveRole(RoleEntity roleEntity);

    /**
     * 根据ID 更新 角色实体
     * @param roleEntity
     * @return
     */
    Integer updateRoleById(RoleEntity roleEntity);

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


    List<String> getRoleNamesByRoleIds(List<Integer> roleIdList);
}
