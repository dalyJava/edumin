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
class RoleEntity extends BasePage implements Serializable {
    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 备注
     */
    private String remark;
    /**
     * 角色目录ID列表
     */
    private List<Integer> menuIdList;

    /**
     * 创建时间
     */
    private String createTime;

}
