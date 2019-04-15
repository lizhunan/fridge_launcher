package com.bysj.lizhunan.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bysj.lizhunan.R;

import androidx.annotation.Nullable;

public class SettingFragment extends PreferenceFragment {

    private static  SettingFragment INSTANCE;

    public SettingFragment() {
    }

    public static SettingFragment newInstance() {
        if(INSTANCE == null){
            INSTANCE = new SettingFragment();
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }
}
