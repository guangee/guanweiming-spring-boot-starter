package com.guanweiming.common.utils;

/**
 * @param <F> 转换前类型
 * @param <T> 转换后类型
 * @author chezhu.xin
 */
@Deprecated
public interface MyAdapter<F, T> {

  /**
   * 转换
   *
   * @param from 源类型
   * @return 转换后的类型
   */
  T translate(F from);
}
