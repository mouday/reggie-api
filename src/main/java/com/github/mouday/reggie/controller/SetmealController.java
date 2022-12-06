package com.github.mouday.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mouday.reggie.common.R;
import com.github.mouday.reggie.dto.SetmealDto;
import com.github.mouday.reggie.entity.Category;
import com.github.mouday.reggie.entity.Setmeal;
import com.github.mouday.reggie.service.CategoryService;
import com.github.mouday.reggie.service.SetmealDishService;
import com.github.mouday.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> addSetmeal(@RequestBody SetmealDto setmealDto) {
        log.info("setmealDto {}", setmealDto);
        setmealService.saveSetmealWithDish(setmealDto);
        return R.success("新增成功");
    }

    /**
     * 套餐分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> getSetmealPage(int page, int pageSize, String name) {

        // 查询
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        // 搜索
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like(Setmeal::getName, name);
        }

        // 排序
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        // 分页
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);

        setmealService.page(pageInfo, queryWrapper);

        // 转为 SetmealDto
        Page<SetmealDto> pageDto = new Page<>(page, pageSize);
        BeanUtils.copyProperties(pageInfo, pageDto, "records");

        List<SetmealDto> list = pageInfo.getRecords()
                .stream()
                .map(item -> {
                    SetmealDto setmealDto = new SetmealDto();

                    BeanUtils.copyProperties(item, setmealDto);

                    // 查询分类名称
                    Category category = categoryService.getById(item.getCategoryId());
                    if (category != null) {
                        setmealDto.setCategoryName(category.getName());
                    }

                    return setmealDto;
                }).collect(Collectors.toList());

        pageDto.setRecords(list);

        return R.success(pageInfo);

    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> removeSetmeal(List<Long> ids){
        setmealService.removeSetmealWithDish(ids);
        return R.success("删除套餐成功");
    }

    /**
     * 获取套餐列表
     * @return
     */
    @GetMapping("/list")
    public R<List<Setmeal>> getSetmealList(Long categoryId, Integer status){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        // 条件
        if(categoryId != null){
            queryWrapper.eq(Setmeal::getCategoryId, categoryId);
        }

        if(status != null){
            queryWrapper.eq(Setmeal::getStatus, status);
        }

        // 排序
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        // 查询
        List<Setmeal> list = setmealService.list(queryWrapper);

        return R.success(list);

    }
}


