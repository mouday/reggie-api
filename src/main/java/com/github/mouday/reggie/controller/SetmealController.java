package com.github.mouday.reggie.controller;

import com.github.mouday.reggie.common.R;
import com.github.mouday.reggie.dto.SetmealDto;
import com.github.mouday.reggie.service.SetmealDishService;
import com.github.mouday.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;


    /**
     * 添加套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> addSetmeal(@RequestBody SetmealDto setmealDto){
        log.info("setmealDto {}", setmealDto);
        setmealService.saveSetmealWithDish(setmealDto);
        return R.success("新增成功");
    }
}
