package com.bysj.lizhunan.core;

import android.content.Context;
import android.net.TrafficStats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class NetMonitor {

    private long rxtxTotal = 0;
    private long mobileRecvSum = 0;
    private long mobileSendSum = 0;
    private long wlanRecvSum = 0;
    private long wlanSendSum = 0;

    private static NetMonitor INSTANCE;

    public static NetMonitor getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new NetMonitor();
        }
        return INSTANCE;
    }

    /**
     * 实时获取网速信息
     *
     * @return
     */
    public double getNetSpeedBytes() {
        long tempSum = TrafficStats.getTotalRxBytes()
                + TrafficStats.getTotalTxBytes();
        long rxtxLast = tempSum - rxtxTotal;
        double totalSpeed = rxtxLast * 1000 / 2000d;
        rxtxTotal = tempSum;
        return totalSpeed;
    }

}
