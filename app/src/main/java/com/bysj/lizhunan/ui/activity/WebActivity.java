package com.bysj.lizhunan.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.bysj.lizhunan.R;
import com.bysj.lizhunan.base.BaseActivity;
import com.bysj.lizhunan.base.Constants;

public class WebActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView titleTv;
    private WebView webView;

    @Override
    protected void initParms(Bundle parms) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView(View view) {
        toolbar = $(R.id.toolbar);
        titleTv = $(R.id.title_tv);
        webView = $(R.id.web);
        /*
         * 设置toolbar
         * */
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("");
        titleTv.setText(getResources().getString(R.string.server));
        setSupportActionBar(toolbar);
    }

    @Override
    protected void doBusiness(Context mContext) {
        webView.loadUrl(Constants.DATA_URL);
    }

    @Override
    protected void widgetClick(View view) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void handleMessage(Message message, int what) {

    }
}
