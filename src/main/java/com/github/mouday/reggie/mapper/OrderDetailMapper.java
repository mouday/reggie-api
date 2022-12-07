package com.github.mouday.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mouday.reggie.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
