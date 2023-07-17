package com.example.lastestcar01.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lastestcar01.domain.Car;
import com.example.lastestcar01.service.CarService;
import com.example.lastestcar01.mapper.CarMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【car】的数据库操作Service实现
* @createDate 2023-07-16 13:38:08
*/
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car>
    implements CarService{

}




