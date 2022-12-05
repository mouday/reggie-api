package com.github.mouday.reggie.controller;


import com.github.mouday.reggie.common.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class shoppingCartController {

    @GetMapping("/list")
    public R<List> getShoppingCartList() {
        return R.success(new ArrayList());
    }
}
