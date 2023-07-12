package com.example.springsecurity_ouaths.service;

import com.example.springsecurity_ouaths.pojo.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Lazy
    @Autowired
    private PasswordEncoder pw;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //从数据库中拿出用户名和密码
        String password = pw.encode("123");
        MyUserDetails userDetails = new MyUserDetails("admin",password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_123"));
        return userDetails;
    }

    @Bean
    public PasswordEncoder pw(){
        return new BCryptPasswordEncoder();
    }
}
