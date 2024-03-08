package com.xmz.bi.model.enums;

/**
 * @author xmz
 * @date 2024-03-08
 */
public enum ChartStatusEnum {
    //wait,running,succeed,failed
    WAIT("等待中","wait"),
    RUNNING("执行中","running"),
    SUCCEED("执行成功","succeed"),
    FAILED("执行失败","failed");

    private final String text;

    private final String value;

    ChartStatusEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
