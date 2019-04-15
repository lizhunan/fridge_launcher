package com.bysj.lizhunan.model;

import com.bysj.lizhunan.bean.App;

import java.util.List;

/**
 * 提供app数据接口
 */
public interface OnGetAppInfo {


    /**
     * 获取数据成功
     * @param appInfos app信息
     */
    void onSuccess(List<App> appInfos);

    /**
     * 获取数据失败
     */
    void onFailed();

    /**
     * 正在加载数据
     */
    void onLoading();

    /**
     * 数据加载完成
     */
    void onLoaded();

}
