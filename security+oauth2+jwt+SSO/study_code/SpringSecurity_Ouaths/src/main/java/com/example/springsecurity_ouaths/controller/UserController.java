package com.example.springsecurity_ouaths.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
*
* @since 1.0.0
*/
@RestController
@RequestMapping("/user")
public class UserController {
@GetMapping("/getCurrentUser")
public Object getCurrentUser(Authentication authentication) {
return authentication.getPrincipal();
}
}