package com.bysj.lizhunan.base;

/**
 * handler what值
 */
public class What {

    /**
     *  资源使用情况
     */
    public static final int USED_DATA_CHANGE = 0x01;

    /**
     *  指定进程资源使用情况
     */
    public static final int PROCESS_USED_DATA_CHANGE = 0x02;

    /**
     * 更新折线图
     */
    public static final int LINE_CHART_CHANGE = 0x03;

    /**
     * 更新指定进程折线图
     */
    public static final int LINE_CHART_PROCESS_CHANGE = 0x04;

    /** 标记异步操作返回时目标界面已经消失时的处理状态 */
    public static final int ACTIVITY_GONE = -1;
}
