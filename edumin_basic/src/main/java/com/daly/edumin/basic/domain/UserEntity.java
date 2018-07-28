package com.daly.edumin.basic.domain;

import com.daly.edumin.basic.common.BasePage;
import com.daly.edumin.basic.config.DataBaseConfig.DatabaseTypeEunm;
import com.daly.edumin.basic.config.DefaultValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by daly on 2018-2-8.
 */
@EqualsAndHashCode(callSuper = false)
public @Data
class UserEntity extends BasePage implements Serializable{

    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户名，即登录账号
     */
    private String username;
    /**
     * 员工编号
     */
    private String employeeId;
    /**
     * 员工姓名
     */
    private String employeeName;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 电话号码
     */
    private String mobile;
    /**
     * 头像图片url
     */
    private String imgUrl;
    /**
     * 启用状态  0：禁用   1：正常',
     */
    private Integer status;
    /**
     * 状态  0：离线   1：在线,
     */
    private Integer loginStatus;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 角色名称列表
     */
    private List<String> roleNameList;
    /**
     * 角色ID列表
     */
    private List<Integer> roleIdList;
    //用户更改记录，用户最后一次上线时间，用户的学习进度，用户的平均在线时长


}
