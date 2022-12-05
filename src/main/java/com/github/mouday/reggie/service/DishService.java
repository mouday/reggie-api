package com.github.mouday.reggie.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mouday.reggie.dto.DishDto;
import com.github.mouday.reggie.entity.Dish;

import java.util.List;

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

    /**
     * 获取菜品 和 对应的口味数据
     * @param id Long
     * @return dishDto
     */
    public DishDto getDishByIdWithDishFlavor(Long id);

    /**
     * 更新菜品 和 对应的口味数据
     * @param dishDto
     */
    public void updateDishWithDishFlavor(DishDto dishDto);

    /**
     * 查询菜品和对应的口味
     * @param list
     * @return
     */
    public List<DishDto> listWithFlavors(List<Dish> list);
}
