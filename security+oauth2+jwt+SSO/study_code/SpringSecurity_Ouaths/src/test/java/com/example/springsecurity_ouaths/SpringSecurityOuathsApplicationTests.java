package com.example.springsecurity_ouaths;

import com.example.springsecurity_ouaths.utils.JWTUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
class SpringSecurityOuathsApplicationTests {

    @Test
    void contextLoads() {
        JWTUtils jwtUtils = new JWTUtils();
        String token = jwtUtils.createToken(1, "admin", Collections.singletonList("admin"));
        System.out.println(token);
        System.out.println("++++++++++++++++++++++++++++++++");
        System.out.println(jwtUtils.getAuth(token));
    }

}
