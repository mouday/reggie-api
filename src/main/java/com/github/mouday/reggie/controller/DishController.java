package com.github.mouday.reggie.controller;


import com.github.mouday.reggie.common.R;
import com.github.mouday.reggie.dto.DishDto;
import com.github.mouday.reggie.service.DishFlavorService;
import com.github.mouday.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

}
