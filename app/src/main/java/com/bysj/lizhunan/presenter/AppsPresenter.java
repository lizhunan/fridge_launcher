package com.bysj.lizhunan.presenter;

import com.bysj.lizhunan.base.BaseHandler;
import com.bysj.lizhunan.bean.App;
import com.bysj.lizhunan.model.AppModel;
import com.bysj.lizhunan.model.OnGetAppInfo;
import com.bysj.lizhunan.ui.fragment.IGetData;

import java.util.List;

/**
 * 提供App数据信息
 */
public class AppsPresenter {

    private IGetData<List<App>> iGetData;
    private AppModel appModel;
    private BaseHandler handler;

    public AppsPresenter() {
    }

    public AppsPresenter(IGetData<List<App>> iGetData,BaseHandler handler) {
        this.iGetData = iGetData;
        this.handler = handler;
        this.appModel = new AppModel();
    }

    /**
     * 提供数据
     *
     * @param i 数据类型
     */
    public void getData(int i,String appName) {
        appModel.data(i, new OnGetAppInfo() {
            @Override
            public void onSuccess(final List<App> appInfos) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iGetData.showSuccess(appInfos);
                    }
                });
            }

            @Override
            public void onFailed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iGetData.showFail();
                    }
                });
            }

            @Override
            public void onLoading() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iGetData.showProgress();
                    }
                });
            }

            @Override
            public void onLoaded() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iGetData.hintProgress();
                    }
                });
            }
        },appName);
    }

}
