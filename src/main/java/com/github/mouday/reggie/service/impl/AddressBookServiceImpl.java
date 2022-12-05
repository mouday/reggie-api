package com.github.mouday.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mouday.reggie.entity.AddressBook;
import com.github.mouday.reggie.mapper.AddressBookMapper;
import com.github.mouday.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl
        extends ServiceImpl<AddressBookMapper, AddressBook>
        implements AddressBookService {
}
