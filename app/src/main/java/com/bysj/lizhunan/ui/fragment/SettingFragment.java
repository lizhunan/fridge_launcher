package com.bysj.lizhunan.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bysj.lizhunan.R;
import com.bysj.lizhunan.base.LauncherApplication;

import androidx.annotation.Nullable;

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener,
        Preference.OnPreferenceChangeListener {

    private static  SettingFragment INSTANCE;
    private SharedPreferences settingsPreference;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SettingFragment() {
        settingsPreference = PreferenceManager.getDefaultSharedPreferences(LauncherApplication.getContext());
        editor = settingsPreference.edit();
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

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }
}
