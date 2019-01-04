package com.guanweiming.common.utils;

/**
 * 监听器
 *
 * @param <T>
 */
@Deprecated
public interface MyListener<T> {
    /**
     * 通知对象完成任务
     *
     * @param data 传递数据
     */
    void notify(T data);
}
