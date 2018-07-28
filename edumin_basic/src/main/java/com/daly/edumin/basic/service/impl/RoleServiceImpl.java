package com.daly.edumin.basic.service.impl;

import com.daly.edumin.basic.domain.RoleEntity;
import com.daly.edumin.basic.domain.RoleMenuEntity;
import com.daly.edumin.basic.mapper.RoleMapper;
import com.daly.edumin.basic.mapper.RoleMenuMapper;
import com.daly.edumin.basic.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daly on 2018-2-28.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    /**
     * 根据一对多关系，插入数据
     * @param roleId
     * @param menuIds
     */
    public void saveRoleMenu(Integer roleId,List<Integer> menuIds){
        List<RoleMenuEntity> roleMenuEntityList = new ArrayList<>();
        for(Integer temp:menuIds){
            RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
            roleMenuEntity.setRoleId(roleId);
            roleMenuEntity.setMenuId(temp);
            roleMenuEntityList.add(roleMenuEntity);
        }
        roleMenuMapper.saveRoleMenu(roleMenuEntityList);
    }

    /**
     * 根据选中的Ids批量删除
     *
     * @param roleIdsArr
     * @return
     */
    @Override
    public Integer delRoleByIds(Integer[] roleIdsArr) {
        Integer flag = 0;
        flag += roleMapper.delRoleByRoleIds(roleIdsArr);
        flag += roleMenuMapper.delRoleMenuByRoleIds(roleIdsArr);
        return flag;
    }

    /**
     * 添加或保存角色
     *
     * @param roleEntity
     * @return
     */
    @Override
    public String saveOrUpdateRole(RoleEntity roleEntity) {
        List<Integer> menuIdsList = roleEntity.getMenuIdList();
        if(roleEntity.getRoleId() != null ){
            roleMapper.updateRoleById(roleEntity);
            Integer roleId = roleEntity.getRoleId();
            Integer[] roleIdsArr = {roleId};
            roleMenuMapper.delRoleMenuByRoleIds(roleIdsArr);
            saveRoleMenu(roleId,menuIdsList);
            return "更新成功";
        }else{
            roleMapper.saveRole(roleEntity);
            Integer roleId = roleEntity.getRoleId();
            Integer[] roleIdsArr = {roleId};
            roleMenuMapper.delRoleMenuByRoleIds(roleIdsArr);
            saveRoleMenu(roleId,menuIdsList);
            return "保存成功";
        }
    }

    /**
     * 获取所有的roleList
     *
     * @return
     */
    @Override
    public List<RoleEntity> getRoleList() {
        return roleMapper.getRoleList();
    }

    /**
     * 根据roleFilter  查询roleList
     *
     * @param roleFilter
     * @return
     */
    @Override
    public List<RoleEntity> getRoleListByFilter(RoleEntity roleFilter) {
        List<RoleEntity> roleList = roleMapper.getRoleListByFilter(roleFilter);
        List<Integer> menuIdList = new ArrayList<>();
        if(roleFilter.getRoleId() != 0L && roleFilter.getRoleId() != null){
            menuIdList = roleMenuMapper.getMenuIdsByRoleId(roleFilter.getRoleId());
        }
        //update's role of menuList
        if(!roleList.isEmpty()){
            roleList.get(0).setMenuIdList(menuIdList);
        }
        return roleList;
    }





}
