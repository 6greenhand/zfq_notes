package com.example.springsecurity_ouaths.config;

import com.example.springsecurity_ouaths.pojo.MyUserDetails;
import com.example.springsecurity_ouaths.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 授权服务器配置
 *
 * @since 1.0.0
ResourceServerConfig.java
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends
        AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    //用来配置用户详情服务
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws
            Exception {
        clients.inMemory()
//配置client_id
                .withClient("admin")
//配置client-secret
                .secret(passwordEncoder.encode("112233"))
//配置redirect_uri，用于授权成功后跳转
                .redirectUris("http://localhost:8083/login")
//配置申请的权限范围
                .scopes("all")
//配置grant_type，表示授权类型
                .authorizedGrantTypes("authorization_code")
        //自动授权配置
                .autoApprove(true)
//配置grant_type，表示授权类型
                .authorizedGrantTypes("authorization_code","password","refresh_token");

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 获取密钥需要身份认证，使用单点登录时必须配置
        security.tokenKeyAccess("isAuthenticated()");
    }

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(myUserDetailsService)
                //配置存储令牌策略
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter);
    }


}

