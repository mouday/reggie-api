package com.github.mouday.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mouday.reggie.common.R;
import com.github.mouday.reggie.entity.Category;
import com.github.mouday.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     *
     * @return
     */
    @PostMapping
    public R<String> addCategory(@RequestBody Category category) {
        log.info("category: {}", category);

        categoryService.save(category);

        return R.success("新增分类成功");
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> getCategoryList(int page, int pageSize) {
        Page<Category> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Category::getSort);

        categoryService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 根据id 删除分类
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteCategoryById(Long ids) {
        log.info("deleteCategoryById: {}", ids);

        // categoryService.removeById(ids);
        categoryService.remove(ids);

        return R.success("分类删除成功");
    }


    /**
     * 更新分类
     */
    @PutMapping
    public R<String> updateCategoryById(@RequestBody Category category) {
        categoryService.updateById(category);
        return R.success("更新成功");
    }

    /**
     * 根据类型查询分类
     */
    @GetMapping("/list")
    public R<List<Category>> getAllCategory(Category category) {

        // 查询
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        if (category.getType() != null) {
            queryWrapper.eq(Category::getType, category.getType());
        }

        // 排序
        queryWrapper.orderByDesc(Category::getSort)
                .orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);

        return R.success(list);
    }

}
