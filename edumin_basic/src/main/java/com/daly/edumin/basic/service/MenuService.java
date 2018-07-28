package com.daly.edumin.basic.service;

import com.daly.edumin.basic.domain.MenuEntity;

import java.util.List;

/**
 * Created by daly on 2018-2-26.
 */
public interface MenuService {
    /**
     * 获取用户的 所有权限
     * @param roleIdsList
     * @returng
     */
    List<String> getAllPerms(List<Integer> roleIdsList);

    /**
     * 根据Filter获取menus
     * @param menuFilter
     * @return
     */
    List<MenuEntity> getMenuListByFilter(MenuEntity menuFilter);

    /**
     * 根据menuIds进行批量删除
     * @param menusArr
     * @return
     */
    Integer delMenuByMenuIds(Integer[] menusArr);

    /**
     * 保存菜单实体
     * 根据ID 更新 菜单实体
     * @param menuEntity
     * @return
     */
    String saveOrUpdateMenu(MenuEntity menuEntity);

    /**
     * 获取树形数据menu
     * @return
     */
    List<MenuEntity> getMenuTreeListNoBtn();

    /**
     *获取所有的菜单 包括按钮
     * 根据roleId的值获取到menuId的值，然后控制是否选中
     * @return
     */
    List<MenuEntity> getMenuTreeListAll(Integer roleId);

    /**
     * 获取所有menu
     * @return
     */
    List<MenuEntity> getMenuAllList();

    /**
     * 获取token用户的roleIdList 菜单列表 -- 树形菜单
     */
    List<MenuEntity> getMenuNameListByRoleIds(List<Integer> userIdsList);

}
