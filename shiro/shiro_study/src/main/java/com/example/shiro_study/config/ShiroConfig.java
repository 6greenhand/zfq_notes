package com.example.shiro_study.config;

import com.example.shiro_study.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AllSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class ShiroConfig {
    @Autowired
    private MyRealm myRealm;
    //配置 SecurityManager
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        //1 创建 defaultWebSecurityManager 对象
        DefaultWebSecurityManager defaultWebSecurityManager = new
                DefaultWebSecurityManager();
        //2 创建加密对象，并设置相关属性
        HashedCredentialsMatcher matcher = new
                HashedCredentialsMatcher();
        //2.1 采用 md5 加密
        matcher.setHashAlgorithmName("md5");
        //2.2 迭代加密次数
        matcher.setHashIterations(3);

        //3 将加密对象存储到 myRealm 中
        myRealm.setCredentialsMatcher(matcher);

//        //4、 创建认证对象，并设置认证策略
//        ModularRealmAuthenticator modularRealmAuthenticator = new
//                ModularRealmAuthenticator();
//        //默认是AtLeastOneSuccessfulStrategy,有一个验证成功就可以，这里设置成全验证成功才可以
//        modularRealmAuthenticator.setAuthenticationStrategy(new
//                AllSuccessfulStrategy());
//
//        //5 把谁策略添加到 defaultWebSecurityManager
//        defaultWebSecurityManager.setAuthenticator(modularRealmAuthenticator);


        //6 设置rememberMe
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        //6 将 myRealm 存入 defaultWebSecurityManager 对象
        //添加多个Realm
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(myRealm);
        defaultWebSecurityManager.setRealms(realms);


        //5 返回
        return defaultWebSecurityManager;
    }

    //设置Shiro的Cookie管理对象
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //设置cookie
        cookieRememberMeManager.setCookie(simpleCookie());
        //Java中设置cookie加密密钥的方法
        cookieRememberMeManager.setCipherKey("1234567890987654".getBytes());

        return cookieRememberMeManager;
    }


    //设置cookie属性
    public SimpleCookie simpleCookie(){
        //创建了一个名为 "rememberMe" 的 SimpleCookie 对象
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //设置跨域
        //cookie.setDomain(domain);
        //cookie将对所有路径有效
        simpleCookie.setPath("/");
        //JavaScript无法访问这个cookie。这是一种安全措施，可以防止XSS攻击。
        simpleCookie.setHttpOnly(true);
        //设置cookie的最大存活时间为30天
        simpleCookie.setMaxAge(30*24*60*60);
        return simpleCookie;
    }

    //配置 Shiro 内置过滤器拦截范围
    @Bean
    public DefaultShiroFilterChainDefinition
    shiroFilterChainDefinition(){

        DefaultShiroFilterChainDefinition definition = new
                DefaultShiroFilterChainDefinition();
        //设置不认证可以访问的资源
        definition.addPathDefinition("/myController/userLogin","anon");
        definition.addPathDefinition("/myController/login","anon");
        //配置登出过滤器
        definition.addPathDefinition("/logout","logout");

        //设置需要进行登录认证的拦截范围
        definition.addPathDefinition("/**","authc");
        //添加存在用户的过滤器（rememberMe）
        definition.addPathDefinition("/**","user");
        return definition;
    }

}
