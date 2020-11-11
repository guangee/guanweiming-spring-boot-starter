package com.guanweiming.common.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


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
    @Min(value = 1, message = "页码值从1开始")
    @NotNull(message = "页码不允许为空")
    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer page = 1;

    /**
     * 每页数量
     */
    @NotNull(message = "每页数量不允许为空")
    @ApiModelProperty(value = "每页数量", example = "10")
    private Integer size = 10;
}