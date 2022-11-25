package com.github.mouday.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mouday.reggie.entity.Dish;
import com.github.mouday.reggie.mapper.DishMapper;
import com.github.mouday.reggie.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl
        extends ServiceImpl<DishMapper, Dish>
        implements DishService {
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
}
