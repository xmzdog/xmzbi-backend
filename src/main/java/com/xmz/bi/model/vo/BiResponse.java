package com.xmz.bi.model.vo;

import lombok.Data;

/**
 * bi 的返回结果
 * @author xmz
 * @date 2023-12-03
 */
@Data
public class BiResponse {

    private String genChart;

    private String genResult;

    private Long chartId;
}
