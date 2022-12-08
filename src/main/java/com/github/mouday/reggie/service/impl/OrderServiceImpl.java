package com.github.mouday.reggie.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mouday.reggie.common.BaseContext;
import com.github.mouday.reggie.common.CustomException;
import com.github.mouday.reggie.entity.*;
import com.github.mouday.reggie.mapper.OrderMapper;
import com.github.mouday.reggie.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl
        extends ServiceImpl<OrderMapper, Orders>
        implements OrderService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private UserService userService;


    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 提交订单
     * @param orders
     */
    @Override
    @Transactional
    public void submit(Orders orders) {
        // 获取当前用户
        Long currentUserId = BaseContext.getCurrentUserId();
        User currentUser = userService.getById(currentUserId);

        // 获取购物车中的数据
        List<ShoppingCart> shoppingCartList = shoppingCartService.getListByUserId(currentUserId);
        if (shoppingCartList == null || shoppingCartList.size() == 0) {
            throw new CustomException("购物车没有商品");
        }

        // 获取用户地址
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        if (addressBook == null) {
            throw new CustomException("用户地址无效");
        }

        // 订单号
        long orderId = IdWorker.getId();

        // 计算金额
        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> orderDetails = shoppingCartList.stream().map(item -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setName(item.getName());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());

            // 金额 * 份数
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());

            return orderDetail;
        }).collect(Collectors.toList());

        // 数据写入订单表
        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2); // 2待派送

        // 总金额
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setUserId(currentUserId);
        orders.setUserName(currentUser.getName());
        orders.setNumber(String.valueOf(orderId));
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress(addressBook.getDetail());
        super.save(orders);


        // 数据写入订单详细表
        orderDetailService.saveBatch(orderDetails);

        // 清空购物车
        shoppingCartService.cleanByUserId(currentUserId);
    }
}
