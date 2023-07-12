package com.example.springsecurity_ouaths_sso_demo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
@EnableOAuth2Sso
public class SpringSecurityOuathsSsoDemo01Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityOuathsSsoDemo01Application.class, args);
    }

}
