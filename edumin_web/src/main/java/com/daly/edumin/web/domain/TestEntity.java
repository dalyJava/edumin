package com.daly.edumin.web.domain;

import com.daly.edumin.basic.common.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by daly on 2018/7/11.
 */
@EqualsAndHashCode(callSuper = false)
public @Data
class TestEntity extends BasePage implements Serializable {
    private String id;
    private String name;
}
