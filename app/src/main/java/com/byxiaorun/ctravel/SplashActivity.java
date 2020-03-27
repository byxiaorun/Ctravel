package com.byxiaorun.ctravel;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.byxiaorun.ctravel.Utils.DButils;

import java.util.Calendar;
/**
 * Created by byxiaorun on 2020/1/4/.
 */
public class SplashActivity extends AppCompatActivity {
    private TextView tvtime;
    private DButils db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        //getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_splash);
        tvtime=findViewById(R.id.time);
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        String applicationName = getApplicationName();
        tvtime.setText(year+"  |  "+applicationName);
        db = DButils.getInstance(SplashActivity.this);
        Thread myThread=new Thread(){
            @Override
            public void run() {
                try{
                    sleep(2000);//休眠2秒
                    Intent it=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(it);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程

    }
    public String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }
}


