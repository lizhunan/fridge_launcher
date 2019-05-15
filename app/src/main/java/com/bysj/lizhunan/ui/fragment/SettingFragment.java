package com.bysj.lizhunan.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bysj.lizhunan.R;
import com.bysj.lizhunan.base.LauncherApplication;
import com.bysj.lizhunan.ui.activity.WebActivity;

import androidx.annotation.Nullable;

import java.util.List;

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener,
        Preference.OnPreferenceChangeListener {

    private static SettingFragment INSTANCE;
    private SharedPreferences settingsPreference;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SettingFragment() {
        settingsPreference = PreferenceManager.getDefaultSharedPreferences(LauncherApplication.getContext());
        editor = settingsPreference.edit();
    }

    public static SettingFragment newInstance() {
        if (INSTANCE == null) {
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
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        final String key = preference.getKey();

        switch (key) {
            case "pref_key_open_service_settings":
                startActivity(new Intent(getContext(), WebActivity.class));
                break;
            case "pref_key_logout_settings":
                try {
                    PackageManager pm = LauncherApplication.getContext().getPackageManager();
                    List<PackageInfo> hiddenApp = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
                    for (int j = 0; j < hiddenApp.size(); j++) {
                        if (LauncherApplication.mDPM.isApplicationHidden(LauncherApplication.mComponentName,
                                hiddenApp.get(j).packageName)) {
                            LauncherApplication.mDPM.setApplicationHidden(LauncherApplication.mComponentName,
                                    hiddenApp.get(j).packageName, false);
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), getContext().getString(R.string.no_permission), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        return false;
    }
}
