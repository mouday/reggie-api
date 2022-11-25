package com.github.mouday.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mouday.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    public boolean hasCategory(Long categoryId);
}
