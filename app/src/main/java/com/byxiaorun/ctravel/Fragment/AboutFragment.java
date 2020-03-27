package com.byxiaorun.ctravel.Fragment;


import android.content.pm.PackageInfo;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.byxiaorun.ctravel.R;

import java.util.Calendar;

/**
 * Created by byxiaorun on 2020/1/4
 */
public class AboutFragment extends Fragment {
    TextView version,copyright;
    public AboutFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        version=view.findViewById(R.id.version);
        copyright=view.findViewById(R.id.copyright);
        try {
            PackageInfo info=view.getContext().getPackageManager().getPackageInfo(view.getContext().getPackageName(),0);
            version.setText("V"+info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        copyright.setText("Copyright 2020-"+year+" byxiaorun");

        return view;
    }
}
