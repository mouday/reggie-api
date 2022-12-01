package com.github.mouday.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mouday.reggie.entity.SetmealDish;
import com.github.mouday.reggie.mapper.SetmealDishMapper;
import com.github.mouday.reggie.service.SetmealDishService;
import org.springframework.stereotype.Service;

@Service
public class SetmealDishServiceImpl
        extends ServiceImpl<SetmealDishMapper, SetmealDish>
        implements SetmealDishService {
}
