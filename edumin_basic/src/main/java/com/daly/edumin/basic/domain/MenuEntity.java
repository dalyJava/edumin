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
class MenuEntity extends BasePage implements Serializable {
    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 父菜单ID，一级菜单为0
     */
    private Integer parentId;

    /**
     * 父菜单名称
     */
    private String parentName;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;

    /**
     * 类型     0：目录   1：菜单   2：按钮
     */
    private Integer type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * ztree属性
     */
    private Boolean open;
    /**
     * 是否选中
     */
    private Boolean checked;
    /**
     * 子菜单
     */
    private List<MenuEntity> children;


}
