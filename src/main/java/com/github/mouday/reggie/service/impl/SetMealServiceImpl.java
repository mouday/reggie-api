package com.github.mouday.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mouday.reggie.dto.SetmealDto;
import com.github.mouday.reggie.entity.Setmeal;
import com.github.mouday.reggie.entity.SetmealDish;
import com.github.mouday.reggie.mapper.SetmealMapper;
import com.github.mouday.reggie.service.SetmealDishService;
import com.github.mouday.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetMealServiceImpl
        extends ServiceImpl<SetmealMapper, Setmeal>
        implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 查询分类下是否有套餐
     *
     * @param categoryId
     * @return
     */
    @Override
    public boolean hasCategory(Long categoryId) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getCategoryId, categoryId);
        int count = this.count(queryWrapper);

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 保存套餐及其菜品
     *
     * @param setmealDto
     */
    @Transactional
    @Override
    public void saveSetmealWithDish(SetmealDto setmealDto) {
        // 保存基本信息 insert
        this.save(setmealDto);

        // 保存菜品信息 insert
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        List<SetmealDish> list = setmealDishes.stream()
                .map(item -> {
                    item.setSetmealId(setmealDto.getId());
                    return item;
                }).collect(Collectors.toList());

        setmealDishService.saveBatch(list);

    }
}
