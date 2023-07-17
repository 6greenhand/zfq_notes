package com.example.shiro_study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shiro_study.domain.User;
import com.example.shiro_study.mapper.UserMapper;
import com.example.shiro_study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author admin
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-07-13 11:27:49
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserInfoByName(String Name) {

        return userMapper.getUserInfoByName(Name);
    }
}




