package com.github.mouday.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mouday.reggie.dto.DishDto;
import com.github.mouday.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    /**
     * 查询分类下是否有菜品
     * @param categoryId
     * @return
     */
    public boolean hasCategory(Long categoryId);

    /**
     * 保存菜品 和 对应的口味数据
     * @param dishDto
     * @return
     */
    public void saveDishWithDishFlavor(DishDto dishDto);
}
