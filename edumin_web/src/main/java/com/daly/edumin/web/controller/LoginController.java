package com.daly.edumin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.daly.edumin.basic.common.ResultDTO;
import com.daly.edumin.basic.config.DefaultValue;
import com.daly.edumin.basic.domain.UserEntity;
import com.daly.edumin.basic.service.UserService;
import com.daly.edumin.web.domain.TestEntity;
import com.daly.edumin.web.service.TestService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daly on 2018-2-8.
 */
@Controller
public class LoginController {
    private  Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 登录
     */
    @ResponseBody
    @RequestMapping(value = "userLogin",method =RequestMethod.POST )
    public ResultDTO login(@RequestBody JSONObject userJSON)throws IOException {
        ResultDTO resultDTO= new ResultDTO();
        try{
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(userJSON.getString("userName"), userJSON.getString("password"));
            subject.login(token);
        }catch (UnknownAccountException e) {
            resultDTO.setInfo(DefaultValue.CODE_FAIL,e.getMessage().toString());
            return resultDTO;
        }catch (IncorrectCredentialsException e) {
            resultDTO.setInfo(DefaultValue.CODE_FAIL,e.getMessage().toString());
            return resultDTO;
        }catch (LockedAccountException e) {
            resultDTO.setInfo(DefaultValue.CODE_FAIL,e.getMessage().toString());
            return resultDTO;
        }catch (AuthenticationException e) {
            resultDTO.setInfo(DefaultValue.CODE_FAIL,"账户验证失败");
            return resultDTO;
        }
        resultDTO.setInfo(DefaultValue.CODE_SUCCESS,"用户登录成功");
        return  resultDTO;
    }

    /**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return
     */
    @RequestMapping(value = "/unauth")
    @ResponseBody
    public ResultDTO unauth() {
        ResultDTO resultDTO= new ResultDTO();
        resultDTO.setInfo(1000,"未登录");
        return resultDTO;
    }
}
