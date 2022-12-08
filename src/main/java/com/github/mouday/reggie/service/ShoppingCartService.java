package com.github.mouday.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mouday.reggie.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {
    public ShoppingCart getShoppingCart(ShoppingCart shoppingCart);

    /**
     * 获取指定用户的购物车列表
     * @param currentUserId
     * @return
     */
    List<ShoppingCart> getListByUserId(Long currentUserId);

    /**
     * 清空指定用户的购物车
     * @param currentUserId
     */
    void cleanByUserId(Long currentUserId);
}
