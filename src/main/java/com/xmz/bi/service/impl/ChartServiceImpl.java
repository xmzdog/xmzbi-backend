package com.xmz.bi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmz.bi.mapper.ChartMapper;
import com.xmz.bi.model.entity.Chart;
import com.xmz.bi.model.enums.ChartStatusEnum;
import com.xmz.bi.service.ChartService;
import org.springframework.stereotype.Service;

/**
* @author 86188
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2023-11-12 15:35:39
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{



    public void handleChartUpdateError(long chartId, String execMessage){
        Chart updateChartResult = new Chart();
        updateChartResult.setId(chartId);
        updateChartResult.setStatus(ChartStatusEnum.FAILED.getValue());
        updateChartResult.setExecuteMessage(execMessage);
        boolean b = this.updateById(updateChartResult);
        if (!b){
            log.error("更新图表状态失败"+chartId+","+execMessage);
        }
    }
}




