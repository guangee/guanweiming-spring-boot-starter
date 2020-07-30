package com.guanweiming.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author guanweiming
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPage {

    /**
     * 当前页码
     */
    private Integer page = 1;

    /**
     * 每页数量
     */
    private Integer size = 10;
}