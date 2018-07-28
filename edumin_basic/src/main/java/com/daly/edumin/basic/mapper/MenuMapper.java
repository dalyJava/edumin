package com.daly.edumin.basic.mapper;

import com.daly.edumin.basic.domain.MenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by daly on 2018-2-26.
 */
@Mapper
public interface MenuMapper {

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
     * 获取所有menu
     * @return
     */
    List<MenuEntity> getAllMenuList();

    /**
     *获取所有的菜单 不包括按钮
     * @return
     */
    List<MenuEntity> getMenuListNobutton();
    /**
     * 保存菜单实体
     * @param menuEntity
     * @return
     */
    Integer saveMenu(MenuEntity menuEntity);

    /**
     * 根据ID 更新 菜单实体
     * @param menuEntity
     * @return
     */
    Integer updateMenuById(MenuEntity menuEntity);

    /**
     * 获取用户菜单列表
     * 根据角色
     */
    List<MenuEntity> getMenuListByRoleIds(List<Integer> roleIdsList);
}
