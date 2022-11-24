package com.github.mouday.reggie.controller;

import com.github.mouday.reggie.common.R;
import com.github.mouday.reggie.entity.Category;
import com.github.mouday.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @return
     */
    @PostMapping
    public R<String> addCategory(@RequestBody Category category){
        log.info("category: {}", category);

        categoryService.save(category);

        return R.success("新增分类成功");
    }

}
