package com.xmz.bi.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 图表信息表
 * @TableName chart
 */
@TableName(value ="chart")
@Data
public class Chart implements Serializable {
    /**
     * id

     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
<<<<<<< HEAD
     * userId
     */
    private Long userId;


    /**
=======
>>>>>>> github/master
     * 分析目标

     */
    private String goal;

    /**
     * 图表数据
     */
    private String chartData;

    /**
     * 图表类型
     */
    private String chartType;

    /**
<<<<<<< HEAD
     * 图表名称
     */
    private String name;

    /**
=======
>>>>>>> github/master
     * 生成的表数据
     */
    private String genChart;

    /**
     * 生成的分析结论
     */
    private String genResult;

    /**
     * 图表生成状态
     */
    private String status;

    /**
     * 图表执行信息
     */
    private String executeMessage;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}