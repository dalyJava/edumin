package com.daly.edumin.basic.mapper;


import com.daly.edumin.basic.domain.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by daly on 2018-2-25.
 */
@Mapper
public interface UserRoleMapper {
    /**
     * 通过UserId获取roleIdsList
     * @param userId
     * @return
     */
    List<Integer> getUserRoleByUserId(Integer userId);

    /**
     * 批量保存 UserRoleEntity
     * @param userRoleEntityList
     * @return
     */
    Integer saveUserRole(List<UserRoleEntity> userRoleEntityList);

    /**
     * 根据 userIds 批量删除 UserRoleEntity
     * @param userIdsArr
     * @return
     */
    Integer delUserRoleByUserIds(Integer[] userIdsArr);

    /**
     * 根据用户ID查询 角色Id列表
     * @param userId
     * @return
     */
    List<Integer> queryRoleIdByUserId(Integer userId);
}
