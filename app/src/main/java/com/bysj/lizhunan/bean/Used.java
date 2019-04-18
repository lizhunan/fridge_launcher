package com.bysj.lizhunan.bean;

/**
 * 资源使用
 */
public class Used {

    private int memoryUsed;
    private int cpuUsed;
    private int netUsed;
    private int memoryPer;
    private int cpuPer;
    private int netPer;
    private int currMemory;
    private int currCpu;
    private int currNet;

    public int getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(int memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public int getCpuUsed() {
        return cpuUsed;
    }

    public void setCpuUsed(int cpuUsed) {
        this.cpuUsed = cpuUsed;
    }

    public int getNetUsed() {
        return netUsed;
    }

    public void setNetUsed(int netUsed) {
        this.netUsed = netUsed;
    }

    public int getMemoryPer() {
        return memoryPer;
    }

    public void setMemoryPer(int memoryPer) {
        this.memoryPer = memoryPer;
    }

    public int getCpuPer() {
        return cpuPer;
    }

    public void setCpuPer(int cpuPer) {
        this.cpuPer = cpuPer;
    }

    public int getNetPer() {
        return netPer;
    }

    public void setNetPer(int netPer) {
        this.netPer = netPer;
    }

    public int getCurrMemory() {
        return currMemory;
    }

    public void setCurrMemory(int currMemory) {
        this.currMemory = currMemory;
    }

    public int getCurrCpu() {
        return currCpu;
    }

    public void setCurrCpu(int currCpu) {
        this.currCpu = currCpu;
    }

    public int getCurrNet() {
        return currNet;
    }

    public void setCurrNet(int currNet) {
        this.currNet = currNet;
    }
}
