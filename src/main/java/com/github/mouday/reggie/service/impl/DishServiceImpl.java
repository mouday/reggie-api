package com.github.mouday.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mouday.reggie.dto.DishDto;
import com.github.mouday.reggie.entity.Dish;
import com.github.mouday.reggie.entity.DishFlavor;
import com.github.mouday.reggie.mapper.DishMapper;
import com.github.mouday.reggie.service.DishFlavorService;
import com.github.mouday.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl
        extends ServiceImpl<DishMapper, Dish>
        implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 查询分类下是否有菜品
     *
     * @param categoryId
     * @return
     */
    @Override
    public boolean hasCategory(Long categoryId) {
        // select count(*) from dish where category_id = ?
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId, categoryId);
        int count = this.count(queryWrapper);

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 保存菜品 和 对应的口味数据
     * @param dishDto
     * @return
     */
    @Transactional
    @Override
    public void saveDishWithDishFlavor(DishDto dishDto) {

        // 保存菜品
        this.save(dishDto);

        // 保存菜品对应的口味数据
        Long dishId = dishDto.getId();

        List<DishFlavor> list = dishDto.getFlavors().stream().map(item -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(list);
    }
}
