package com.daly.edumin.basic.service.impl;

import com.alibaba.fastjson.JSON;
import com.daly.edumin.basic.domain.MenuEntity;
import com.daly.edumin.basic.mapper.MenuMapper;
import com.daly.edumin.basic.mapper.RoleMenuMapper;
import com.daly.edumin.basic.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daly on 2018-2-26.
 */
@Service
public class MenuServiceImpl implements MenuService {
    private final static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;



    public Boolean hasChild(List<MenuEntity> allMenuList, MenuEntity menuEntity){
        boolean flag = false;
        for(MenuEntity temp : allMenuList){
            if(temp.getParentId() == menuEntity.getMenuId())
                flag = true;
            }
        return flag;
    }

    public List<MenuEntity> findAllParents(List<MenuEntity> allMenuList,Integer parentId){
        List<MenuEntity> parentsMenu = new ArrayList<>();
        for(MenuEntity menu : allMenuList){
            if(menu.getParentId() == parentId)
                parentsMenu.add(menu);
        }
        return parentsMenu;
    }

    public List<MenuEntity> findChildByPid(List<MenuEntity> allMenuList,Integer parentId) {
        List<MenuEntity> childMenu = new ArrayList<>();
        for(MenuEntity menu : allMenuList){
            if(menu.getParentId() == parentId){
                childMenu.add(menu);
            }
        }
        return childMenu;
    }

    public void bindChildByParent(List<MenuEntity> allMenuList,MenuEntity menu){
        if(this.hasChild(allMenuList,menu)){
            List<MenuEntity> childlist = this.findChildByPid(allMenuList,menu.getMenuId());
            menu.setChildren(childlist);
            for (MenuEntity temp:childlist) {
                if(this.hasChild(allMenuList,temp)){
                    this.bindChildByParent(allMenuList,temp);
                }
            }
        }
    }

    /**
     * 获取用户的 所有权限
     *
     * @param roleIdsList
     * @returng
     */
    @Override
    public List<String> getAllPerms(List<Integer> roleIdsList) {
        return menuMapper.getAllPerms(roleIdsList);
    }

    /**
     * 根据Filter获取menus
     *
     * @param menuFilter
     * @return
     */
    @Override
    public List<MenuEntity> getMenuListByFilter(MenuEntity menuFilter) {
        return menuMapper.getMenuListByFilter(menuFilter);
    }

    /**
     * 根据menuIds进行批量删除
     *
     * @param menusArr
     * @return
     */
    @Override
    public Integer delMenuByMenuIds(Integer[] menusArr) {
        return menuMapper.delMenuByMenuIds(menusArr);
    }

    /**
     * 保存菜单实体
     *根据ID 更新 菜单实体
     * @param menuEntity
     * @return
     */
    @Override
    public String saveOrUpdateMenu(MenuEntity menuEntity) {
//        menuEntity.get?
        if(menuEntity.getMenuId() != null ){
            menuMapper.updateMenuById(menuEntity);
            return "更新成功";
        }else {
            menuMapper.saveMenu(menuEntity);
            return "保存成功";
        }
    }

    /**
     * 获取树形菜单 全部
     */
    public List<MenuEntity> getMenuTreeListAll(Integer roleId) {
        //控制选中状态
        List<Integer> menuIdsList = new ArrayList<>();
        if(roleId != 0L && roleId != null){
            //选中的menuIdsList
            menuIdsList = roleMenuMapper.getMenuIdsByRoleId(roleId);
        }
        List<MenuEntity> allMenuList = menuMapper.getAllMenuList();
        //根据menuIds改变check属性
        if(!menuIdsList.isEmpty() && !allMenuList.isEmpty() ){
            for (MenuEntity temp:allMenuList) {
                Integer menuId = temp.getMenuId();
                if(menuIdsList.contains(menuId)){
                    temp.setChecked(true);
                }
            }
        }
        List<MenuEntity> rootList = new ArrayList<>();
        if (!allMenuList.isEmpty()) {
            List<MenuEntity> rootMenuList = this.findAllParents(allMenuList, 0);
            for (int i = 0; i < rootMenuList.size(); i++) {
                MenuEntity tree = rootMenuList.get(i);
                //绑定孩子
                if (this.hasChild(allMenuList, tree)) {
                    List<MenuEntity> list = this.findChildByPid(allMenuList, tree.getMenuId());
                    tree.setChildren(list);
                    this.bindChildByParent(allMenuList,tree);
                }
                rootList.add(tree);
            }
            System.out.println(JSON.toJSONString(rootList));
        }else {
            logger.info("MenuServiceImpl.getMenuTreeList -------- 没有数据！");
        }
        return rootList;
    }


    /**
     * 获取树形菜单 不包含btn
     */
    public List<MenuEntity> getMenuTreeListNoBtn() {
        List<MenuEntity> allMenuList = menuMapper.getMenuListNobutton();
        List<MenuEntity> rootList = new ArrayList<>();
        if (allMenuList != null) {
            List<MenuEntity> rootMenuList = this.findAllParents(allMenuList, 0);
            for (int i = 0; i < rootMenuList.size(); i++) {
                MenuEntity tree = rootMenuList.get(i);
                 //绑定孩子
                if (this.hasChild(allMenuList, tree)) {
                    List<MenuEntity> list = this.findChildByPid(allMenuList, tree.getMenuId());
                    tree.setChildren(list);
                    this.bindChildByParent(allMenuList,tree);
                }
                rootList.add(tree);
            }
            System.out.println(JSON.toJSONString(rootList));
        }else {
            logger.info("MenuServiceImpl.getMenuTreeList -------- 没有数据！");
        }
        return rootList;
    }

    /**
     * 获取所有menu
     *
     * @return
     */
    @Override
    public List<MenuEntity> getMenuAllList() {
        return menuMapper.getAllMenuList();
    }


    /**
     * 获取用户菜单列表entity
     *获取两级菜单
     * @param roleIdsList
     */
    @Override
    public List<MenuEntity> getMenuNameListByRoleIds(List<Integer> roleIdsList) {

        List<MenuEntity> allMenuList = menuMapper.getMenuListByRoleIds(roleIdsList);
        //获取第一层菜单
        List<MenuEntity> OneLvlMenusList = new ArrayList<>();
        for (MenuEntity temp :allMenuList ) {
            if(temp.getParentId() == 0l)
                OneLvlMenusList.add(temp);
        }
        if(OneLvlMenusList.isEmpty()){
            return null;
        }
        for(MenuEntity menuEntity : OneLvlMenusList){
            Integer menuId = menuEntity.getMenuId();
            List<MenuEntity> childMenusList = new ArrayList<>();
            for(MenuEntity temp : allMenuList){
                if(temp.getParentId() == menuId)
                    childMenusList.add(temp);
            }
            if(!childMenusList.isEmpty())
                menuEntity.setChildren(childMenusList);
        }
        return OneLvlMenusList;

    }

}
