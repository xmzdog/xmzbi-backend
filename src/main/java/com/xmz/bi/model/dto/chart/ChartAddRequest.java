package com.xmz.bi.model.dto.chart;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/xmzdog/xmzbi-backend">程序员xmz</a>
 * 
 */
@Data
public class ChartAddRequest implements Serializable {


    /**
     * 分析目标
     */
    private String goal;

    /**
     * userId
     */
    private Long userId;

    /**
     * 图表数据
     */
    private String chartData;

    /**
     * 图表类型
     */
    private String chartType;


    private static final long serialVersionUID = 1L;
}