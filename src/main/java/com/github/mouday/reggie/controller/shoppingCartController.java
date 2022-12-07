package com.github.mouday.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.mouday.reggie.common.BaseContext;
import com.github.mouday.reggie.common.R;
import com.github.mouday.reggie.entity.ShoppingCart;
import com.github.mouday.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 */
@RestController
@RequestMapping("/shoppingCart")
public class shoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 查看购物车
     * 用户自己看自己的购物车数据
     *
     * @return
     */
    @GetMapping("/list")
    public R<List> getShoppingCartList() {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();

        // 条件
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentUserId());

        // 排序
        queryWrapper.orderByDesc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        return R.success(list);
    }

    /**
     * 添加菜品或套餐到购物车
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> addShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        // 设置当前用户
        shoppingCart.setUserId(BaseContext.getCurrentUserId());

        // 查询是否存在相同菜品或套餐，存在则合并更新,不存在则新增
        ShoppingCart shoppingCartRow = shoppingCartService.getShoppingCart(shoppingCart);
        if (shoppingCartRow != null) {
            shoppingCartRow.setNumber(shoppingCartRow.getNumber() + 1);
            shoppingCartService.updateById(shoppingCartRow);
        } else {
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
            shoppingCartRow = shoppingCart;
        }

        return R.success(shoppingCartRow);
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("/clean")
    public R<String> clean() {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentUserId());

        shoppingCartService.remove(queryWrapper);

        return R.success("购物车清空成功");
    }
}
