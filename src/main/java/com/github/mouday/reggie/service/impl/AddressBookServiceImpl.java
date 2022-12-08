package com.github.mouday.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mouday.reggie.entity.AddressBook;
import com.github.mouday.reggie.mapper.AddressBookMapper;
import com.github.mouday.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl
        extends ServiceImpl<AddressBookMapper, AddressBook>
        implements AddressBookService {

    /**
     * 重置用户的所有默认地址
     *
     * @param currentUserId
     */
    @Override
    public void resetDefaultAddressByUserId(Long currentUserId) {
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, currentUserId);
        wrapper.set(AddressBook::getIsDefault, 0);
        //SQL:update address_book set is_default = 0 where user_id = ?
        super.update(wrapper);
    }

    /**
     * 设置用户的默认地址
     *
     * @param currentUserId
     * @param id
     */
    @Override
    public void updateDefaultAddressByUserId(Long currentUserId, Long id) {

        //SQL:update address_book set is_default = 1 where id = ? and user_id = ?
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();

        wrapper.eq(AddressBook::getUserId, currentUserId);
        wrapper.eq(AddressBook::getId, id);
        wrapper.set(AddressBook::getIsDefault, 1);

        super.update(wrapper);
    }

    /**
     * 设置用户的默认地址
     * @param currentUserId
     * @param addressId
     */
    @Override
    public void setDefaultAddressByUserId(Long currentUserId, Long addressId) {

        this.resetDefaultAddressByUserId(currentUserId);

        this.updateDefaultAddressByUserId(currentUserId, addressId);

    }
}
