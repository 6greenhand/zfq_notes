package com.example.shiro_study.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/myController")
public class MyController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/userLogin")
    public String userLogin(String name, String password,@RequestParam(required = false)boolean rememberMe, Model model){
        Subject subject = SecurityUtils.getSubject();
        //2 封装请求数据到 token 对象中
        AuthenticationToken token = new
                UsernamePasswordToken(name,password,rememberMe);
        //3 调用 login 方法进行登录认证
        try {
            subject.login(token);
            model.addAttribute("user",name);
            return "main";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("登录失败");
            return "login";
        }
    }

    //登录认证验证 rememberMe

    //是否拥有此角色
    @RequiresRoles("admin")
    @GetMapping("userLoginRm")
    public String userLogin(Model model) {
        model.addAttribute("user","rememberMe");

        return "main";
    }
}
