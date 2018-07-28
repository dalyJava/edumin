package com.daly.edumin.basic.domain;

import com.daly.edumin.basic.common.BasePage;
import com.daly.edumin.basic.config.DataBaseConfig.DatabaseTypeEunm;
import com.daly.edumin.basic.config.DefaultValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by daly on 2018-2-8.
 */
@EqualsAndHashCode(callSuper = false)
public @Data
class RoleMenuEntity extends BasePage implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 菜单ID
     */
    private Integer menuId;
}
