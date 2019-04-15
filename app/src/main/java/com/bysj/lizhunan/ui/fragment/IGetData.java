package com.bysj.lizhunan.ui.fragment;

import com.bysj.lizhunan.bean.App;

import java.util.List;

/**
 * 获取数据接口
 */
public interface IGetData<T> {

    /**
     * 显示数据
     * @param appInfos app数据
     */
    void showSuccess(T appInfos);
    void showFail();

    /**
     * 显示进度
     */
    void showProgress();

    /**
     * 关闭进度
     */
    void hintProgress();

    void refreshUI();

}
