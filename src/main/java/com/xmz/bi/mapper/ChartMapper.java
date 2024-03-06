package com.xmz.bi.mapper;

import com.xmz.bi.model.entity.Chart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
* @author 86188
* @description 针对表【chart(图表信息表)】的数据库操作Mapper
* @createDate 2023-11-12 15:35:39
* @Entity com.xmz.bi.model.entity.Chart
*/
public interface ChartMapper extends BaseMapper<Chart> {
    Map<String,Object> queryChartData(String querySql);
}




