package com.github.mouday.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mouday.reggie.dto.SetmealDto;
import com.github.mouday.reggie.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {

    public boolean hasCategory(Long categoryId);

    /**
     * 保存套餐及其菜品
     * @param setmealDto
     */
    public void saveSetmealWithDish(SetmealDto setmealDto);
}
