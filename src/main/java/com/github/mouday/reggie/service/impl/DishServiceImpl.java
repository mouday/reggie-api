package com.github.mouday.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mouday.reggie.dto.DishDto;
import com.github.mouday.reggie.entity.Dish;
import com.github.mouday.reggie.entity.DishFlavor;
import com.github.mouday.reggie.mapper.DishMapper;
import com.github.mouday.reggie.service.DishFlavorService;
import com.github.mouday.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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
     *
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

    /**
     * 获取菜品 和 对应的口味数据
     *
     * @param id Long
     * @return
     */
    @Override
    public DishDto getDishByIdWithDishFlavor(Long id) {
        // 查询菜品信息
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        // 查询口味信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());

        List<DishFlavor> list = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(list);

        return dishDto;
    }

    /**
     * 更新菜品 和 对应的口味数据
     * @param dishDto
     */
    @Override
    @Transactional
    public void updateDishWithDishFlavor(DishDto dishDto) {
        // 更新菜品基本信息
        this.updateById(dishDto);

        // 清理对应的口味数据 delete
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        // 更新对应的口味数据 insert
        List<DishFlavor> dishFlavors = dishDto.getFlavors().stream().map(item -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());


        dishFlavorService.saveBatch(dishFlavors);
    }

    /**
     * 查询菜品和对应的口味
     * @param list
     * @return
     */
    @Override
    public List<DishDto> listWithFlavors(List<Dish> list) {
        // 获取id
        List<Long> dishIds = list.stream().map(Dish::getId).collect(Collectors.toList());

        // 查询关联数据
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(DishFlavor::getDishId, dishIds);
        List<DishFlavor> dishFlavors = dishFlavorService.list(queryWrapper);

        // 分组
        Map<Long, List<DishFlavor>> map = dishFlavors.stream().collect(Collectors.groupingBy(DishFlavor::getDishId));

        // 数据转换
        List<DishDto> dishDtoList = list.stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            dishDto.setFlavors(map.get(dishDto.getId()));
            return dishDto;
        }).collect(Collectors.toList());

        return dishDtoList;
    }
}
