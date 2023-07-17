package com.example.lastestcar01.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lastestcar01.domain.Brand;
import com.example.lastestcar01.service.BrandService;
import com.example.lastestcar01.mapper.BrandMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【brand】的数据库操作Service实现
* @createDate 2023-07-16 13:38:08
*/
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand>
    implements BrandService{

}




