package com.example.lastestcar01.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lastestcar01.domain.Model;
import com.example.lastestcar01.service.ModelService;
import com.example.lastestcar01.mapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【model】的数据库操作Service实现
* @createDate 2023-07-16 13:38:08
*/
@Service
public class ModelServiceImpl extends ServiceImpl<ModelMapper, Model>
    implements ModelService{

}




