package com.daly.edumin.basic.mapper;

import com.daly.edumin.basic.domain.RoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by daly on 2018-2-26.
 */
@Mapper
public interface RoleMenuMapper {
    /**
     * 保存角色菜单
     */
    Integer saveRoleMenu(List<RoleMenuEntity> roleMenuEntityList);

    /**
     * 删除RoleMenu
     * @param roleIdsArr
     * @return
     */
    Integer delRoleMenuByRoleIds(Integer[] roleIdsArr);

    /**
     * 根据roleId获取菜单menuId
     * @param roleId
     * @return
     */
    List<Integer> getMenuIdsByRoleId(Integer roleId);

}
