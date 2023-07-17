package com.example.shiro_study.service;

import com.example.shiro_study.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author admin
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-07-13 11:27:49
*/
public interface UserService extends IService<User> {
    /**
     * 根据用户名获取用户信息
     */
    User getUserInfoByName(String Name);
}
