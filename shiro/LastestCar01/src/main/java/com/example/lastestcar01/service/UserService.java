package com.example.lastestcar01.service;

import com.example.lastestcar01.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author admin
* @description 针对表【user】的数据库操作Service
* @createDate 2023-07-16 13:38:08
*/
public interface UserService extends IService<User> {
    User findUserByUsername(String username);
}
