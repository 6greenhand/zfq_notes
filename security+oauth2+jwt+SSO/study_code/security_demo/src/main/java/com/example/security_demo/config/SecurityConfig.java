package com.example.security_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * 配置密码加密方式
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyDeniedHandler myDeniedHandler;


    @Lazy
    @Autowired
    PersistentTokenRepository persistentTokenRepository;


    @Autowired
    private UserServiceImpl userService;

    /**
     * .自定义登录页面
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                //自定义登录页面
                .loginPage("/login.html")
                //当发现/login时认为是登录，必须和表单提交的地址一样。去执行UserServiceImpl
                .loginProcessingUrl("/login")
                //登录成功后跳转页面，POST请求
                .successForwardUrl("/demo")
                //登录失败后跳转页面
                .failureForwardUrl("/toerror");

        http.authorizeHttpRequests()
                //login.html不需要被认证
                .antMatchers("/login.html").permitAll()
                //error.html不需要被认证
                .antMatchers("/error.html").permitAll()
                //访问/main.html需要admin权限
                .antMatchers("/main.html").hasAuthority("admin")
                //访问/main.html需要是角色abc
                .antMatchers("/main.html").hasRole("abc")
                //在实际项目中经常需要放行所有静态资源
                .antMatchers("/js/**","/css/**").permitAll()
                //所有请求都必须被认证，必须登录后被访问
                .anyRequest().authenticated();

        //异常处理
        http.exceptionHandling()
                .accessDeniedHandler(myDeniedHandler);

        //记住我
        http.rememberMe()
                //设置数据源
                .tokenRepository(persistentTokenRepository)
                //设置过期时间
                .tokenValiditySeconds(60)
                //自定义登录逻辑
                .userDetailsService(userService);

        http.logout()
                //默认后面会有?logout
                .logoutSuccessUrl("/login.html");

        //关闭csrf防护
        http.csrf().disable();
    }


    @Autowired
    private DataSource dataSource;

    /**
     * 记住我
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //自动建表，第一次启动时需要，第二次启动时注释掉
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }


}
