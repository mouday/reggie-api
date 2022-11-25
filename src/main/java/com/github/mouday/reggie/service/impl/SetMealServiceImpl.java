package com.github.mouday.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mouday.reggie.entity.SetMeal;
import com.github.mouday.reggie.mapper.SetMealMapper;
import com.github.mouday.reggie.service.SetMealService;
import org.springframework.stereotype.Service;

@Service
public class SetMealServiceImpl
        extends ServiceImpl<SetMealMapper, SetMeal>
        implements SetMealService {

    /**
     * 查询分类下是否有套餐
     *
     * @param categoryId
     * @return
     */
    @Override
    public boolean hasCategory(Long categoryId) {
        LambdaQueryWrapper<SetMeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetMeal::getCategoryId, categoryId);
        int count = this.count(queryWrapper);

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
}
