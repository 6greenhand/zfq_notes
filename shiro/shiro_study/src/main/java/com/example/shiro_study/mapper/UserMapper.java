package com.example.shiro_study.mapper;

import com.example.shiro_study.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author admin
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-07-13 11:27:49
* @Entity generator.domain.User
*/
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where name = #{name}")
    User getUserInfoByName(String name);
}




