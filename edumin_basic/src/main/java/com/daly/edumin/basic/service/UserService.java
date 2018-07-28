package com.daly.edumin.basic.service;

import com.daly.edumin.basic.common.ResultDTO;
import com.daly.edumin.basic.config.TargetDataSource;
import com.daly.edumin.basic.domain.UserEntity;

import java.beans.IntrospectionException;
import java.util.List;

/**
 * Created by daly on 2018-2-8.
 */
public interface UserService {

    /**
     * 保存添加用户
     * 根据用户ID 修改用户信息
     * @param userEntity
     * @return
     */
    ResultDTO saveOrUpdate(UserEntity userEntity);

    /**
     *  根据传入的数组，进行批量操作
     * @param userIdArr
     * @return
     */
    ResultDTO delByUserIds(String[] userIdArr);

    /**
     * 根据员工编号或员工姓名查找
     * @param userFilter
     * @return
     */
    ResultDTO getUserByUserFilter(UserEntity userFilter) throws IntrospectionException;

    /**
     * 通过userId获取用户信息
     * @param userId
     * @return
     */
    ResultDTO getUserInfoById(Integer userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Integer> queryAllMenuId(Integer userId);

    /**
     * 根据用户名，查询系统用户
     */
    UserEntity queryByUserName(String username);
}
