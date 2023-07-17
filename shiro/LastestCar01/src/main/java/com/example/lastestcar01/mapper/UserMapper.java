package com.example.lastestcar01.mapper;

import com.example.lastestcar01.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author admin
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-07-16 13:38:08
* @Entity generator.domain.User
*/
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where username = #{username}")
    public User findUserByUsername(String username);
}




