package com.github.mouday.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mouday.reggie.entity.Orders;
import com.github.mouday.reggie.mapper.OrderMapper;
import com.github.mouday.reggie.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl
        extends ServiceImpl<OrderMapper, Orders>
        implements OrderService {
}
