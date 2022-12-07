package com.github.mouday.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mouday.reggie.entity.OrderDetail;
import com.github.mouday.reggie.mapper.OrderDetailMapper;
import com.github.mouday.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl
        extends ServiceImpl<OrderDetailMapper, OrderDetail>
        implements OrderDetailService {
}
