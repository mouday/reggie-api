package com.github.mouday.reggie.common;


/**
 * 基于ThreadLocal封装的工具类
 * 用于保存和获取当前登录用户id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentUserId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentUserId() {
        return threadLocal.get();
    }
}
