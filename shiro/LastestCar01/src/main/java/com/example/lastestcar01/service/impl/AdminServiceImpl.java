package com.example.lastestcar01.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lastestcar01.domain.Admin;
import com.example.lastestcar01.service.AdminService;
import com.example.lastestcar01.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【admin】的数据库操作Service实现
* @createDate 2023-07-16 13:38:08
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




