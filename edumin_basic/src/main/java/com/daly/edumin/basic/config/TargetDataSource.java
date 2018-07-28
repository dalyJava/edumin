package com.daly.edumin.basic.config;

import java.lang.annotation.*;

/**
 * ClassName:TargetDataSource
 * Date:     2018年07月11日 下午7:42:15
 * @author   Joe
 * @version
 * @since    JDK 1.8
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String name();
}