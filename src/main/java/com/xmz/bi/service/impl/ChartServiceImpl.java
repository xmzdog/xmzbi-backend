package com.xmz.bi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmz.bi.mapper.ChartMapper;
import com.xmz.bi.model.entity.Chart;
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

}




