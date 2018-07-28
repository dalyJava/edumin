package com.daly.edumin.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daly.edumin.basic.common.ResultDTO;
import com.daly.edumin.basic.domain.UserEntity;
import com.daly.edumin.basic.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.beans.IntrospectionException;

/**
 * Created by daly on 2018-2-8.
 */
@Controller
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;



    @ResponseBody
    @RequestMapping(value = "user/list/filter", method = RequestMethod.POST)
    public ResultDTO getUserListByUserFilter(@RequestBody JSONObject userJSON) throws IntrospectionException {
        logger.info("getUserListByUserFilter=============================start:"+userJSON);
        UserEntity userFilter =JSONObject.parseObject(userJSON.toJSONString(),UserEntity.class);
        ResultDTO resultDTO =  userService.getUserByUserFilter(userFilter);
        return resultDTO;
    }

    @ResponseBody
    //@RequiresPermissions("sys:user:delete")
    @RequestMapping(value = "user/goDel", method = RequestMethod.POST)
    public ResultDTO delUserByUserIds(@RequestBody JSONObject userJSON){
        logger.info("delUserByUserIds=============================start:"+userJSON.toString());
        ResultDTO resultDTO =  userService.delByUserIds(userJSON.getString("ids").split(","));
        return resultDTO;
    }

    @ResponseBody
    @RequestMapping(value = "user/goInsert", method = RequestMethod.POST)
    public ResultDTO goInsert(@RequestBody JSONObject userJSON){
        logger.info("save=============================start:"+userJSON);
        UserEntity userEntity =JSONObject.toJavaObject(userJSON,UserEntity.class);
        ResultDTO resultDTO =  userService.saveOrUpdate(userEntity);
        return resultDTO;
    }

    @ResponseBody
    @RequestMapping(value = "user/goUpdate", method = RequestMethod.POST)
    public ResultDTO goUpdate(@RequestBody JSONObject userJSON){
        logger.info("save=============================start:"+userJSON);
        UserEntity userEntity =JSONObject.toJavaObject(userJSON,UserEntity.class);
        ResultDTO resultDTO =  userService.saveOrUpdate(userEntity);
        return resultDTO;
    }

    @ResponseBody
    @RequestMapping(value = "user/getUserInfo", method = RequestMethod.POST)
    public ResultDTO getUserInfo(@RequestBody JSONObject userJSON){
        logger.info("save=============================start:"+userJSON);
        UserEntity userFilter =JSONObject.parseObject(userJSON.toJSONString(),UserEntity.class);
        ResultDTO resultDTO =  userService.getUserInfoById(userFilter.getUserId());
        return resultDTO;
    }

}
