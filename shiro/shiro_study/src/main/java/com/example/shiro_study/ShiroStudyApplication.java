package com.example.shiro_study;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@SpringBootApplication
@MapperScan("com.example.shiro_study.mapper")
public class ShiroStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShiroStudyApplication.class, args);
		//三次盐md5加密
		System.out.println(new Md5Hash("123456", "salt", 3));

	}

}
