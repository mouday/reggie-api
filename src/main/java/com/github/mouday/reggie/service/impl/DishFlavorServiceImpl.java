package com.github.mouday.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mouday.reggie.entity.DishFlavor;
import com.github.mouday.reggie.mapper.DishFlavorMapper;
import com.github.mouday.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;


@Service
public class DishFlavorServiceImpl
        extends ServiceImpl<DishFlavorMapper, DishFlavor>
        implements DishFlavorService {
}
