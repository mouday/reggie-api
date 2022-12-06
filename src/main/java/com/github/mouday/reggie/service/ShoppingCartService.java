package com.github.mouday.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mouday.reggie.entity.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart> {
    public ShoppingCart getShoppingCart(ShoppingCart shoppingCart);
}
