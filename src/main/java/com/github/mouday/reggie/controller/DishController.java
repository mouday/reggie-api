package com.github.mouday.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mouday.reggie.common.R;
import com.github.mouday.reggie.dto.DishDto;
import com.github.mouday.reggie.entity.Category;
import com.github.mouday.reggie.entity.Dish;
import com.github.mouday.reggie.service.CategoryService;
import com.github.mouday.reggie.service.DishFlavorService;
import com.github.mouday.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;


    @Autowired
    private CategoryService categoryService;

    /**
     * 添加菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> addDish(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());

        dishService.saveDishWithDishFlavor(dishDto);

        return R.success(null);
    }

    /**
     * 菜品分页
     */
    @GetMapping("/page")
    public R<Page> getDishPage(int page, int pageSize, String name) {

        // 条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like(Dish::getName, name);
        }

        // 排序
        queryWrapper.orderByDesc(Dish::getUpdateTime)
                .orderByDesc(Dish::getId);

        // 分页
        Page<Dish> pageInfo = new Page<>(page, pageSize);

        // 查询
        dishService.page(pageInfo, queryWrapper);

        // 查询分类名称
        Page<DishDto> dishDtoPageInfo = new Page<>();

        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPageInfo, "records");

        List<Dish> dishRecords = pageInfo.getRecords();

        // N + 1 次查询
        // List<DishDto> dishDtoRecords = dishRecords.stream().map(item -> {
        //
        //     DishDto dishDto = new DishDto();
        //     BeanUtils.copyProperties(item, dishDto);
        //
        //     Long categoryId = item.getCategoryId();
        //     Category category = categoryService.getById(categoryId);
        //
        //     if (category != null) {
        //         dishDto.setCategoryName(category.getName());
        //     }
        //
        //     return dishDto;
        // }).collect(Collectors.toList());

        // 优化 2次 查询
        // 取 Category.id
        Set<Long> categoryIds = dishRecords.stream()
                .map(Dish::getCategoryId).filter(item -> {
                    return item != null;
                }).collect(Collectors.toSet());

        // 取映射：Category.id => Category
        List<Category> categories = categoryService.listByIds(categoryIds);
        Map<Long, Category> categorieMap = categories.stream()
                .collect(Collectors.toMap(Category::getId, o -> o));

        // 类型转换 Dish => DishDto
        List<DishDto> dishDtoRecords = dishRecords.stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();
            Category category = categorieMap.get(categoryId);

            if (category != null) {
                dishDto.setCategoryName(category.getName());
            }

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPageInfo.setRecords(dishDtoRecords);

        return R.success(dishDtoPageInfo);
    }

    /**
     * 根据id获取菜品
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getDishById(@PathVariable long id) {
        DishDto dishDto = dishService.getDishByIdWithDishFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 更新菜品
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> updateDishById(@RequestBody DishDto dishDto) {

        dishService.updateDishWithDishFlavor(dishDto);

        return R.success("更新成功");
    }

    /**
     * 根据分类查询菜品
     *
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> getDishList(Dish dish) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        // 条件
        if (dish.getCategoryId() != null) {
            queryWrapper.eq(Dish::getCategoryId, dish.getCategoryId());
        }

        // 仅显示起售的菜品
        queryWrapper.eq(Dish::getStatus, 1);

        // 排序
        queryWrapper.orderByAsc(Dish::getSort)
                .orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        List<DishDto> dishDtolist = dishService.listWithFlavors(list);

        return R.success(dishDtolist);
    }
}
