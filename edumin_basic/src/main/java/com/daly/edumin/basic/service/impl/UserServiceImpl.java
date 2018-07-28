package com.daly.edumin.basic.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.daly.edumin.basic.common.ResultDTO;
import com.daly.edumin.basic.config.DefaultValue;
import com.daly.edumin.basic.config.TargetDataSource;
import com.daly.edumin.basic.domain.UserEntity;
import com.daly.edumin.basic.domain.UserRoleEntity;
import com.daly.edumin.basic.mapper.RoleMapper;
import com.daly.edumin.basic.mapper.UserMapper;
import com.daly.edumin.basic.mapper.UserRoleMapper;
import com.daly.edumin.basic.service.UserService;
import com.daly.edumin.basic.util.CommonUtils;
import com.daly.edumin.basic.util.VueUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by daly on 2018-2-8.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    public Integer  saveUserRole(Integer userId,List<Integer> roleIdList){
        List<UserRoleEntity> userRoleEntityList = new ArrayList<>();
        for (Integer roleId : roleIdList) {
            UserRoleEntity temp = new UserRoleEntity();
            temp.setUserId(userId);
            temp.setRoleId(roleId);
            userRoleEntityList.add(temp);
        }
        return userRoleMapper.saveUserRole(userRoleEntityList);
    }

    /**
     * 根据用户ID 修改用户信息
     *
     * @param userEntity
     * @return
     */
    @Override
    public ResultDTO saveOrUpdate(UserEntity userEntity) {
        ResultDTO resultDTO = new ResultDTO();
        Integer resCodeInsert = 0;
        Integer resCodeUpdate = 0;
        if(userEntity.getUserId()==null ){
            resCodeInsert = userMapper.insertUser(userEntity);
        }else{
            resCodeUpdate = userMapper.updateById(userEntity);
        }

        if(resCodeInsert == -1 || resCodeUpdate == -1)
            resultDTO.setInfo(DefaultValue.CODE_FAIL,DefaultValue.MSG_FAIL);
        else{
            if(resCodeInsert == 1)
                resultDTO.setInfo(DefaultValue.CODE_SUCCESS,DefaultValue.MSG_SUCCESS,"{\"userId\":\""+userEntity.getUserId()+"\"}");
            if(resCodeUpdate == 1)
                resultDTO.setInfo(DefaultValue.CODE_SUCCESS,DefaultValue.MSG_SUCCESS);
        }
        return resultDTO;
    }

    /**
     * 根据传入的数组，进行批量操作
     * @param userIdArr
     * @return
     */
    @Override
    public ResultDTO delByUserIds(String[] userIdArr) {
        ResultDTO resultDTO = new ResultDTO();
        Integer resCode = userMapper.delUsersByUserIds(userIdArr);
        if(resCode > 0)
            resultDTO.setInfo(DefaultValue.CODE_SUCCESS,DefaultValue.MSG_SUCCESS,"共删除"+userIdArr.length+"条数据");
        //flag += userRoleMapper.delUserRoleByUserIds(userIdArr);
        return resultDTO;
    }

    /**
     * 根据员工编号或员工姓名查找
     *
     * @param userFilter
     * @return
     */
    @Override
    @TargetDataSource(name= DefaultValue.DATASOURCE_EDUMIN_BASIC)
    public ResultDTO getUserByUserFilter(UserEntity userFilter) throws IntrospectionException {
        ResultDTO resultDTO = new ResultDTO();
        //处理filter数据，主要是区分模糊查询和精准查询
        if(!userFilter.getFilterParam().isEmpty()){
            if(userFilter.getFilterParam().containsKey("searchFilter")){//模糊查询
                String str = (String)userFilter.getFilterParam().get("searchFilter");
                if(str != "" && str != null && !str.equals("[]")){
                    Map searchFilter = (Map) JSONObject.parse(str.substring(1,str.length()-1));
                    userFilter.setSearchFilter(searchFilter);
                }
            }
            if(userFilter.getFilterParam().containsKey("columnFilters")){//精准查询
                String str = (String)userFilter.getFilterParam().get("columnFilters");
                Map<String,String> columnFilterMap = VueUtils.getMapByListMap(str);
                userFilter.setColumnFilterMap(columnFilterMap);
            }
        }

        //设置分页数据
        Page<UserEntity> page = PageHelper.startPage(userFilter.getPageNum(), userFilter.getPageSize());
        List<UserEntity> userEntityList = userMapper.getUserByUserFilter(userFilter);
        if(userEntityList.isEmpty()){
            resultDTO.setInfo(DefaultValue.CODE_FAIL,DefaultValue.MSG_NULL);
            return resultDTO;
        }
        //分页数据返回
        userEntityList.get(0).setPage(page);
        //查询用户角色
        for(UserEntity userEntity : userEntityList){
            Integer userId = userEntity.getUserId();
            List<Integer> roleIds = userRoleMapper.getUserRoleByUserId(userId);
            userEntity.setRoleIdList(roleIds);
            //返回时间格式化输出
            if(StrUtil.isNotEmpty(userEntity.getBirthday()))
                userEntity.setBirthday(userEntity.getBirthday().substring(0,10));
        }
        //表格返回的filter 列表数据
        String[] filterNameArr = {"username","birthday"};
        Map<String,String[]> filterParamMap = getAllFilterParamMap(filterNameArr,userFilter);
        Map<String,List<Map<String,String>>> resFilterMap =VueUtils.getFilters(filterParamMap);
        resultDTO.setResFilterMap(resFilterMap);
        //返回数据
        resultDTO.setInfo(DefaultValue.CODE_SUCCESS,userEntityList);
        return resultDTO;
    }

    @Override
    public ResultDTO getUserInfoById(Integer userId) {
        ResultDTO resultDTO = new ResultDTO();
        UserEntity userEntity = userMapper.getUserInfoById(userId);
        if(userEntity == null)
            resultDTO.setInfo(DefaultValue.CODE_FAIL,DefaultValue.MSG_NULL);
        resultDTO.setInfo(DefaultValue.CODE_SUCCESS,DefaultValue.MSG_SUCCESS,userEntity);
        return resultDTO;
    }

    //获取vue表格的过滤列表
    public Map<String,String[]> getAllFilterParamMap(String[] filterNameArr,UserEntity userFilter){
        userFilter.getColumnFilterMap().put("allColData","true");
        Map<String,String[]> allFilterParamMap = new HashMap<>();
        Map<String,String> resFilterMap = userMapper.getAllResFilter(userFilter);
        for(String str : filterNameArr){
            String[] strArr = ((String)resFilterMap.get(str)).split(",");
            allFilterParamMap.put(str,strArr);
        }
        return allFilterParamMap;
    }


    /**
     * 查询用户的所有菜单ID
     *
     * @param userId
     */
    @Override
    public List<Integer> queryAllMenuId(Integer userId) {
        return userMapper.queryAllMenuIdByUserId(userId);
    }

    /**
     * 根据username，查询系统用户
     *
     * @param username
     */
    @Override
    public UserEntity queryByUserName(String username) {
        UserEntity userEntity = userMapper.queryByUserName(username);
        List<Integer> roleIds = null;
        if( userEntity != null){
            //长日期年月日时分秒 转换成 短日期年月日
            if(StrUtil.isNotEmpty(userEntity.getCreateTime())){
                userEntity.setCreateTime(StrUtil.sub(userEntity.getCreateTime(), 0, 10));
            }
            if( userEntity.getUserId() != null ){
                roleIds = userRoleMapper.queryRoleIdByUserId(userEntity.getUserId());
                userEntity.setRoleIdList(roleIds);
            }
        }
        if( roleIds.size()>0){
            userEntity.setRoleNameList(roleMapper.getRoleNamesByRoleIds(roleIds));
        }
        return userEntity;
    }
}
