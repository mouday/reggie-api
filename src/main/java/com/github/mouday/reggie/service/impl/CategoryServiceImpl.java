package com.github.mouday.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mouday.reggie.common.CustomException;
import com.github.mouday.reggie.entity.Category;
import com.github.mouday.reggie.mapper.CategoryMapper;
import com.github.mouday.reggie.service.CategoryService;
import com.github.mouday.reggie.service.DishService;
import com.github.mouday.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl
        extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetMealService setMealService;

    /**
     * 移除分类
     *
     * @param id
     */
    @Override
    public void remove(Long id) {
        // 如果关联菜品，抛出异常
        if(dishService.hasCategory(id)){
            throw new CustomException("已经关联菜品，不能删除分类");
        }

        // 如果关联套餐，抛出异常
        if(setMealService.hasCategory(id)){
            throw new CustomException("已经关联套餐，不能删除分类");
        }

        // 删除分类
        this.removeById(id);
    }
}
