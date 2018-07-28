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
class UserRoleEntity  extends BasePage implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 角色ID
     */
    private Integer roleId;
}
