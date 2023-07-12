package com.example.security_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderAdvice {
    @Bean
    public PasswordEncoder setpw(){
        return new BCryptPasswordEncoder();
    }
}
