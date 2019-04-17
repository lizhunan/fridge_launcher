package com.bysj.lizhunan.ui.fragment.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bysj.lizhunan.R;
import com.bysj.lizhunan.base.BaseListAdapter;
import com.bysj.lizhunan.base.BaseViewHolder;
import com.bysj.lizhunan.bean.App;

import java.util.ArrayList;
import java.util.List;

public class AppListAdapter extends BaseListAdapter<App> {

    private List<App> appList = new ArrayList<>();

    public AppListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getLayoutId() {
        return R.layout.apps_item;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, int position) {
        TextView appName = holder.getView(R.id.app_name);
        TextView appProcess = holder.getView(R.id.app_process);
        TextView appPkg = holder.getView(R.id.app_pkg);
        TextView appVer = holder.getView(R.id.app_ver);
        TextView appSize = holder.getView(R.id.app_size);
        Switch ctrlSt = holder.getView(R.id.is_ctrl_st);
        ImageView appIcon = holder.getView(R.id.app_iv);

        App app = getDataList().get(position);

        appName.setText(app.getAppName());
        appProcess.setText(app.getProcessName());
        appPkg.setText(app.getPackageName());
        appVer.setText(app.getVersion());
        appSize.setText(app.getSize());
        ctrlSt.setChecked(app.isCtrl());
        appIcon.setImageDrawable(app.getImage());
    }
}
