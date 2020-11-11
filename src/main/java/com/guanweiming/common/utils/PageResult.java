package com.guanweiming.common.utils;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author guanweiming
 */
@Data
public class PageResult<T> implements Serializable {

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 状态码
     */
    private int status;

    /**
     * 数据
     */
    private List<T> data;

    /**
     * 数量
     */
    private Long recordsTotal;


    protected PageResult(long total, List<T> list) {
        this.recordsTotal = total;
        data = list;
    }

    protected PageResult() {
    }

    public static <T> PageResult<T> success(long total, List<T> list) {
        return new PageResult<>(total, list);
    }


    public static <T> PageResult<T> error(String msg) {
        PageResult<T> result = new PageResult<>();
        result.msg = msg;
        result.status = ResponseCode.ERROR.getCode();
        return result;
    }

    public static <T> PageResult<T> success(PageInfo<T> pageInfo) {
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
}
