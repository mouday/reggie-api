package com.github.mouday.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mouday.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
