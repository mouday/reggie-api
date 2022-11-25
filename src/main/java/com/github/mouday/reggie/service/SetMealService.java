package com.github.mouday.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mouday.reggie.entity.SetMeal;

public interface SetMealService extends IService<SetMeal> {

    public boolean hasCategory(Long categoryId);
}
