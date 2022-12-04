package com.github.mouday.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mouday.reggie.entity.User;

public interface UserService extends IService<User> {
    public User getUserByPhone(String phone);
}
