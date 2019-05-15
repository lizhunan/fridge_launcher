package com.bysj.lizhunan.ui.fragment.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.CompoundButton;
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

    private OnCheckedCtrlListener onCheckedCtrlListener;
    private Context context;
    private SharedPreferences sp;

    public AppListAdapter(Context mContext) {
        super(mContext);
        this.context = mContext;
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setOnCheckedCtrlListener(OnCheckedCtrlListener onCheckedCtrlListener) {
        this.onCheckedCtrlListener = onCheckedCtrlListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.apps_item;
    }

    @Override
    public void onBindItemHolder(BaseViewHolder holder, final int position) {
        TextView appName = holder.getView(R.id.app_name);
        TextView appProcess = holder.getView(R.id.app_process);
        TextView appPkg = holder.getView(R.id.app_pkg);
        TextView appVer = holder.getView(R.id.app_ver);
        TextView appSize = holder.getView(R.id.app_size);
        final Switch ctrlSt = holder.getView(R.id.is_ctrl_st);
        ImageView appIcon = holder.getView(R.id.app_iv);

        final App app = getDataList().get(position);

        appName.setText(app.getAppName());
        appProcess.setText(app.getProcessName());
        appPkg.setText(app.getPackageName());
        appVer.setText(app.getVersion());
        appSize.setText(app.getSize());
        ctrlSt.setChecked(app.isCtrl());
        if (app.isCtrl()) {
            ctrlSt.setText(context.getResources().getString(R.string.ctrl_ing));
        }else {
            ctrlSt.setText(context.getResources().getString(R.string.no_ctrl));
        }
        ctrlSt.setEnabled(sp.getBoolean(context.getString(R.string.pref_key_is_ctrl_settings),true));
        appIcon.setImageDrawable(app.getImage());
        ctrlSt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ctrlSt.setText(context.getResources().getString(R.string.ctrl_ing));
                }else {
                    ctrlSt.setText(context.getResources().getString(R.string.no_ctrl));
                }
                onCheckedCtrlListener.onCtrlChanged(compoundButton,b,position);
            }
        });

    }

    public interface OnCheckedCtrlListener{
        void onCtrlChanged(CompoundButton compoundButton,boolean b,int position);
    }
}
