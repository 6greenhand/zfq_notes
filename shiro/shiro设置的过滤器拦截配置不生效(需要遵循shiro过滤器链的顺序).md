> **当我们配置 Shiro 内置过滤器拦截范围时，需要严格遵循 Shiro过滤器链的顺序 ，不然配置不会生效的**



***过滤器链顺序：Shiro过滤器链的逻辑顺序应该是：设置不认证可以访问的资源路径、登出过滤器路径、设置需要进行登录认证的拦截范围、添加存在用户的过滤器(rememberMe)。**



```java
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
```

