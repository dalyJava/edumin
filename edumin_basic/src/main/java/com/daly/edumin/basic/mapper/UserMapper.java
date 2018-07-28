package com.daly.edumin.basic.mapper;


import java.util.List;
import java.util.Map;

import com.daly.edumin.basic.common.ResultDTO;
import com.daly.edumin.basic.config.TargetDataSource;
import com.daly.edumin.basic.domain.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by daly on 2018-2-8.
 */
@Mapper
public interface UserMapper {

    Map<String,String> getAllResFilter(UserEntity userFilter);

    /**
     * 保存添加用户
     * @param userEntity
     * @return
     */
    Integer insertUser(UserEntity userEntity);
    /**
     * 根据用户ID 修改用户信息
     * @param userEntity
     * @return
     */
    Integer updateById(UserEntity userEntity);
    /**
     *  根据传入的数组，进行批量操作
     * @param userIdArr
     * @return
     */
    Integer delUsersByUserIds(String[] userIdArr);

    /**
     * 根据员工编号或员工姓名查找
     * @param userFilter
     * @return
     */
    List<UserEntity> getUserByUserFilter(UserEntity userFilter);

    /**
     * 通过userId获取用户信息
     * @param userId
     * @return
     */
    UserEntity getUserInfoById(Integer userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Integer> queryAllMenuIdByUserId(Integer userId);

    /**
     * 根据用户名，查询系统用户
     */
    UserEntity queryByUserName(String username);

    /**
     *修改用户密码
     * @param map
     * @return
     */
    int modifyPassword(Map<String, UserEntity> map);
}
