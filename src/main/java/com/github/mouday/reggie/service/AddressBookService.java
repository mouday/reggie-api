package com.github.mouday.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mouday.reggie.entity.AddressBook;

public interface AddressBookService extends IService<AddressBook> {

    /**
     * 重置用户的所有默认地址
     * @param currentUserId
     */
    void resetDefaultAddressByUserId(Long currentUserId);

    /**
     * 更新用户的默认地址
     * @param currentUserId
     * @param id
     */
    void updateDefaultAddressByUserId(Long currentUserId, Long id);

    /**
     * 设置用户的默认地址
     * @param currentUserId
     * @param id
     */
    void setDefaultAddressByUserId(Long currentUserId, Long id);
}
