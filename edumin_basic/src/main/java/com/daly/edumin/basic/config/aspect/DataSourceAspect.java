package com.daly.edumin.basic.config.aspect;

import com.daly.edumin.basic.config.DataBaseConfig.DatabaseContextHolder;
import com.daly.edumin.basic.config.DataBaseConfig.DatabaseTypeEunm;
import com.daly.edumin.basic.config.TargetDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by daly on 2018/7/10.
 */
@Aspect
@Order(-1)//保证该AOP在@Transactional之前执行
@Component
public class DataSourceAspect {
    private Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);
   // @Before("execution(* com.daly.edumin.basic.service..*.*(..))||execution(* com.daly.edumin.web.service..*.*(..))")
    /**
     * @Description 在方法执行之前执行  @annotation(ds) 会拦截有ds这个注解的方法即有 TargetDataSource这个注解的
     * @param @param point
     * @param @param ds
     * @param @throws Throwable 参数
     * @return void 返回类型
     * @throws
     */
    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, TargetDataSource ds)
            throws Throwable {
        String dsId = ds.name();//获取注解的数据源名称
        if (!DatabaseContextHolder.containsDataSource(dsId)) {
            logger.error("数据源[{}]不存在，使用默认数据源 > {}", ds.name(), point.getSignature());
        }
        else {
            logger.info("Use DataSource : {} > {}", ds.name(),point.getSignature());
            DatabaseContextHolder.setDatabaseType(DatabaseTypeEunm.valueOf(ds.name()));
        }
    }

    /**
     * @Description 在方法执行之后执行  @annotation(ds) 会拦截有ds这个注解的方法即有 TargetDataSource这个注解的
     * @param @param point
     * @param @param ds 参数
     * @return void 返回类型
     * @throws
     */
    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, TargetDataSource ds) {
        logger.debug("Revert DataSource : {} > {}", ds.name(), point.getSignature());
        DatabaseContextHolder.clearDataSourceType();
    }


}