package com.github.mouday.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动填充元数据
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入时自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if(metaObject.hasSetter("createTime")){
            metaObject.setValue("createTime", LocalDateTime.now());
        }

        if(metaObject.hasSetter("updateTime")){
            metaObject.setValue("updateTime", LocalDateTime.now());
        }

        if(metaObject.hasSetter("createUser")){
            metaObject.setValue("createUser", BaseContext.getCurrentUserId());
        }

        if(metaObject.hasSetter("updateUser")){
            metaObject.setValue("updateUser", BaseContext.getCurrentUserId());
        }
    }

    /**
     * 更新时自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        long id = Thread.currentThread().getId();
        log.info("Thread id: {}", id);

        if(metaObject.hasSetter("createUser")){
            metaObject.setValue("createUser", BaseContext.getCurrentUserId());
        }

        if(metaObject.hasSetter("updateUser")){
            metaObject.setValue("updateUser", BaseContext.getCurrentUserId());
        }
    }
}
