package com.xmz.bi.service;

import com.xmz.bi.model.entity.Chart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xmz.bi.model.enums.ChartStatusEnum;

/**
 * @author 86188
 * @description 针对表【chart(图表信息表)】的数据库操作Service
 * @createDate 2023-11-12 15:35:39
 */
public interface ChartService extends IService<Chart> {

    void handleChartUpdateError(long chartId, String execMessage);

}
