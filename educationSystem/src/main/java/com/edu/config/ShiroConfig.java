package com.edu.config;

import com.edu.realm.MyRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
* @Author: wl
* @Description: Shiro配置类
*/

@Configuration
public class ShiroConfig {


/**
     * ShiroFilterFactoryBean 处理拦截资源文件问题
     *
     * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     *
     */

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        //shiroFilterFactoryBean.setLoginUrl("/login.ftl");

        //配置登录过滤器
        shiroFilterFactoryBean.setLoginUrl("/tologin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/tologin");

        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");

        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/user/test", "anon");

        filterChainDefinitionMap.put("/drawImage", "anon");

        filterChainDefinitionMap.put("/user/loadMenuInfo", "anon");
        filterChainDefinitionMap.put("/user/getPersonInfo", "anon");
        filterChainDefinitionMap.put("/user/tolog", "anon");
        filterChainDefinitionMap.put("/user/logout", "anon");


        // 配置退出过滤器
        filterChainDefinitionMap.put("/admin/user/logout", "logout");

        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");

        //登录成功后跳转链接(测试)

/* shiroFilterFactoryBean.setSuccessUrl("/menu");*/


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
}

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myRealm());

        //设置RememberMe管理器;
        securityManager.setRememberMeManager(rememberMeManager());

        return securityManager;
    }


/**
     * 身份认证MyRealm
     */

    @Bean
    public MyRealm myRealm() {
    	MyRealm myRealm = new MyRealm();
        return myRealm;
    }


/**
     * Shiro生命周期处理器
     */

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

/**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions)
     */


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }


/**
     * cookie实现记住密码
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        System.out.println("ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //cookie有效时间1天 ,单位秒
        simpleCookie.setMaxAge(86400);
        return simpleCookie;
    }


/**
     * cookie管理对象
     */

    @Bean
    public CookieRememberMeManager rememberMeManager(){
        System.out.println("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }
}

