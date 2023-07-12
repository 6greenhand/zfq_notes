package com.example.security_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 自定义登录逻辑
 */
@Service
public class UserServiceImpl implements UserDetailsService {


    @Lazy
    @Autowired
    PasswordEncoder pw;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //可以从数据库中查询用户名和密码（这是测试一下就不从数据库中取了）
        if(!"admin".equals(username)){
            throw new UsernameNotFoundException("用户名不存在");
        }

        //有这个用户就再从数据库中查询出其密码
        String password = pw.encode("123");

        /**
         * UserDetails是接口，User是它的实现，所以用User
         * 解释：该方法是 Spring Security 提供的一个实用工具方法。它将逗号分隔的字符串解析为多个权限，
         * 并将每个权限表示为 GrantedAuthority 对象。在这个例子中，字符串 "admin,normal" 被解析成
         * 两个权限： "admin" 和 "normal"。
         */
        return new User(username,password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal，ROLE_abc"));
    }

}
