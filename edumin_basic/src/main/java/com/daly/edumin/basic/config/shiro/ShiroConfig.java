package com.daly.edumin.basic.config.shiro;

import com.daly.edumin.basic.factory.impl.EduFactoryImpl;
import com.daly.edumin.basic.util.PropertiesUtils;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;

/**
 * Created by daly on 2018-2-8.
 */
@Configuration
public class ShiroConfig {

    private String host = PropertiesUtils.getProperty("spring.redis.host");
    private int port = Integer.parseInt(PropertiesUtils.getProperty("spring.redis.port"));
    private int timeout = Integer.parseInt(PropertiesUtils.getProperty("spring.redis.timeout"));
    private String password = PropertiesUtils.getProperty("spring.redis.password");

    /**
    *继承自AuthorizingRealm的自定义Realm,即指定Shiro验证用户登录的类为自定义的UserRealm
     */
    @Bean
    public MyShiroRealm myShiroRealm(){
        return new MyShiroRealm();
    }
    /**
     * 这里主要是设置自定义的单Realm应用,若有多个Realm,可使用'realms'属性代替
     */
  /*  @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager= new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        System.out.println("===========================securityManager is get==========================================");
        return securityManager;
    }*/

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     * @return redisManager
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setTimeout(timeout);
        //redisManager.setPassword(password);
        return redisManager;
    }
    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     * @return cacheManager
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }
    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * <p>
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    //自定义sessionManager
    @Bean
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setSessionDAO(redisSessionDAO());
        return mySessionManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        //Shiro的核心安全接口,这个属性是必须的
        shiroFilter.setSecurityManager(securityManager());
        //要求登录时的链接(可根据项目的URL进行替换),非必须的属性,默认会自动寻找Web工程根目录下的"/login.html"页面
        shiroFilter.setLoginUrl("/unauth");
        //登录成功后要跳转的连接
        shiroFilter.setSuccessUrl("/index.html");
        //用户访问未对其授权的资源时,所显示的连接
        shiroFilter.setUnauthorizedUrl("/403.html");
        //Shiro连接约束配置,即过滤链的定义
        //下面value值的第一个'/'代表的路径是相对于HttpServletRequest.getContextPath()的值来的
        //anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main这种
        //authc：该过滤器下的页面必须验证后才能访问,
        String str = "/statics/**=anon /login.html=anon /sys/login=anon /captcha.jpg=anon /**=authc";
        // 拦截器
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap());
        return shiroFilter;
    }

    @Bean
    public LinkedHashMap<String,String> filterChainDefinitionMap(){
        return eduFactory().createdFilterChainDefinitionMap();
    }

    @Bean
    public EduFactoryImpl eduFactory(){
        return new EduFactoryImpl();
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @DependsOn("lifecycleBeanPostProcessor")
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

}
