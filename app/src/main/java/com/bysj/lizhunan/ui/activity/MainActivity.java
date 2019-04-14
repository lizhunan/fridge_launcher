package com.bysj.lizhunan.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;

import com.bysj.lizhunan.R;
import com.bysj.lizhunan.base.BaseActivity;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigation ;

    @Override
    protected void initParms(Bundle parms) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(View view) {
        navigation = $(R.id.navigation);
    }

    @Override
    protected void doBusiness(Context mContext) {

    }

    @Override
    protected void widgetClick(View view) {

    }

    @Override
    public void handleMessage(Message message) {

    }

    @Override
    protected void setListener() {
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_apps:
                return true;
            case R.id.navigation_statistics:
                return true;
            case R.id.navigation_setting:
                return true;
        }
        return false;
    }
}
