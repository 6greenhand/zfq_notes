package com.example.security_demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DemoController {

//    @PreAuthorize("hasAuthority('role')")
    @RequestMapping("/demo")
    public String demo(){
        return "redirect:main.html";
    }

    @RequestMapping("/toerror")
    public String error(){
        return "redirect:error.html";
    }


}
