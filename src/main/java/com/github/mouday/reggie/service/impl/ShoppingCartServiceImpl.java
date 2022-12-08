package com.github.mouday.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mouday.reggie.entity.ShoppingCart;
import com.github.mouday.reggie.mapper.ShoppingCartMapper;
import com.github.mouday.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl
        extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
        implements ShoppingCartService {

    /**
     * 查询套餐
     *
     * @param shoppingCart
     */
    @Override
    public ShoppingCart getShoppingCart(ShoppingCart shoppingCart) {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();

        // 用户
        if (shoppingCart.getUserId() != null) {
            queryWrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId());
        }

        // 菜品
        if (shoppingCart.getDishId() != null) {
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        }

        // 套餐
        if (shoppingCart.getSetmealId() != null) {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        return super.getOne(queryWrapper);
    }

    /**
     * 获取指定用户的购物车列表
     * @param currentUserId
     * @return
     */
    @Override
    public List<ShoppingCart> getListByUserId(Long currentUserId) {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentUserId);

        return super.list(queryWrapper);

    }

    /**
     * 清空指定用户的购物车
     * @param currentUserId
     */
    @Override
    public void cleanByUserId(Long currentUserId) {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentUserId);
        super.remove(queryWrapper);
    }
}
