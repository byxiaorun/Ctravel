package com.byxiaorun.ctravel;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.byxiaorun.ctravel.Activity.LoginActivity;
import com.byxiaorun.ctravel.Activity.RegisterActivity;
import com.byxiaorun.ctravel.Adapter.RouteAdapter;
import com.byxiaorun.ctravel.Fragment.MenuLeftFragment;
import com.byxiaorun.ctravel.Fragment.RouteFragment;
import com.byxiaorun.ctravel.Utils.DButils;
import com.byxiaorun.ctravel.Utils.RouteDB;
import com.byxiaorun.ctravel.bean.RouteBean;
import com.nineoldandroids.view.ViewHelper;

import com.byxiaorun.ctravel.Fragment.FragmentAdapter;

import java.util.List;
import java.util.zip.Inflater;

/**
 * /**
 * Created by byxiaorun on 2020/1/4/
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    public static FragmentAdapter mAdapter;
    //private FrameLayout mainbodylayout;
    private LinearLayout mainbar;
    private TextView homebar, aboutbar, routebar;
    //导航栏
    private TextView tv_main_title, tv_add;
    private LinearLayout li_title_bar, tv_menu;
    private ViewPager vpager;
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    String username,city,route,reason;
    //侧边栏
    private DrawerLayout mDrawerLayout;
    private MenuLeftFragment mMenuleft;
    RouteDB routeDB;
    private RouteAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        setListener();//监听事件
        setDefault();//设置默认
        initEvents();//侧边栏滑动弹出
        mAdapter = new FragmentAdapter(getSupportFragmentManager());
        vpager = findViewById(R.id.vipager);
        vpager.setAdapter(mAdapter);
        vpager.addOnPageChangeListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            //从设置界面或登录界面传递过来的登录状态
            boolean isLogin = data.getBooleanExtra("isLogin", false);
            if (mMenuleft != null) {//登录成功或退出一句isLogin设置我的界面
                mMenuleft.setLoginParams(isLogin);
            }
        }
    }


    private void setDefault() {
        clearStyle();
        setStyle(0);
    }

    private void init() {
        //mainbodylayout = findViewById(R.id.main_body);
        mainbar = findViewById(R.id.main_bar);
        homebar = findViewById(R.id.homebar);
        aboutbar = findViewById(R.id.aboutbar);
        routebar = findViewById(R.id.routebar);
        tv_menu = findViewById(R.id.tv_menu);
        tv_menu.setVisibility(View.VISIBLE);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("首页");
        li_title_bar = findViewById(R.id.titlebar);
        li_title_bar.setBackgroundColor(Color.parseColor("#303030"));
        mainbar.setVisibility(View.VISIBLE);
        //侧边栏
        mDrawerLayout = findViewById(R.id.id_drawerLayout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.RIGHT);
        tv_add = findViewById(R.id.tv_add);
        tv_add.setOnClickListener(this);
    }

    private void initEvents() {
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals("LEFT")) {
                    float leftScale = 1 - 0.3f * scale;

                    com.nineoldandroids.view.ViewHelper.setScaleX(mMenu, leftScale);
                    com.nineoldandroids.view.ViewHelper.setScaleY(mMenu, leftScale);
                    com.nineoldandroids.view.ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                    com.nineoldandroids.view.ViewHelper.setTranslationX(mContent,
                            0);
                    com.nineoldandroids.view.ViewHelper.setPivotX(mContent, 0);
                    com.nineoldandroids.view.ViewHelper.setPivotY(mContent,
                            0);
                    mContent.invalidate();
                } else {
                    com.nineoldandroids.view.ViewHelper.setTranslationX(mContent,
                            -mMenu.getMeasuredWidth() * slideOffset);
                    com.nineoldandroids.view.ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
                    com.nineoldandroids.view.ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    com.nineoldandroids.view.ViewHelper.setScaleX(mContent, rightScale);
                    com.nineoldandroids.view.ViewHelper.setScaleY(mContent, rightScale);
                }

            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }
        });
    }


    public void OpenMenu(View view) {
        mDrawerLayout.openDrawer(Gravity.LEFT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                Gravity.LEFT);
    }

    private void setListener() {
        for (int i = 0; i < mainbar.getChildCount(); i++) {
            mainbar.getChildAt(i).setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.homebar:
                clearStyle();//清空导航栏样式
                setView(0);
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.routebar:
                clearStyle();//清空导航栏样式
                setView(1);
                vpager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.aboutbar:
                clearStyle();//清空导航栏样式
                setView(2);
                vpager.setCurrentItem(PAGE_THREE);
                break;
            case R.id.tv_add:
                if (DButils.getIsLogin()){
                LayoutInflater inflater=LayoutInflater.from( this );
                View v = inflater.inflate(R.layout.add_route, null, false);
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setNegativeButton("确认",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText et_city=v.findViewById(R.id.et_city);
                        EditText et_route=v.findViewById(R.id.et_route);
                        EditText et_reason=v.findViewById(R.id.et_reason);
                        username=DButils.getUsername();
                        city=et_city.getText().toString();
                        route=et_route.getText().toString();
                        reason=et_reason.getText().toString();
                        if (TextUtils.isEmpty(city)||TextUtils.isEmpty(route)){
                            Toast.makeText(view.getContext(), "路线或城市不能为空", Toast.LENGTH_SHORT).show();
                        }else {
                            RouteDB.getInstance(view.getContext()).saveRoute(username,city,route,reason);
                            mAdapter.setroute();
                            Toast.makeText(view.getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setView(v).create().show();}else {
                    Toast.makeText(view.getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void clearStyle() {
        homebar.setTextColor(Color.parseColor("#909090"));
        homebar.setTextSize(14);
        aboutbar.setTextColor(Color.parseColor("#909090"));
        aboutbar.setTextSize(14);
        routebar.setTextColor(Color.parseColor("#909090"));
        routebar.setTextSize(14);
        tv_add.setVisibility(View.GONE);
        for (int i = 0; i < mainbar.getChildCount(); i++) {
            mainbar.getChildAt(i).setSelected(false);
        }
    }

    private void setView(int index) {
        //removeView();
        setStyle(index);//设置导航栏样式
    }

    private void setStyle(int index) {
        switch (index) {
            case 0:
                homebar.setSelected(true);
                homebar.setTextColor(Color.parseColor("#ffffff"));
                homebar.setTextSize(18);
                tv_main_title.setText(R.string.homename);
                break;
            case 1:
                routebar.setSelected(true);
                routebar.setTextColor(Color.parseColor("#ffffff"));
                routebar.setTextSize(18);
                tv_main_title.setText(R.string.routename);
                tv_add.setText("+");
                tv_add.setTextSize(60);
                tv_add.setVisibility(View.VISIBLE);
                break;
            case 2:
                aboutbar.setSelected(true);
                aboutbar.setTextColor(Color.parseColor("#ffffff"));
                aboutbar.setTextSize(18);
                tv_main_title.setText(R.string.aboutname);
                break;
            default:
                break;
        }
    }
//    private void removeView() {
//        for (int i=0;i<mainbodylayout.getChildCount();i++){
//            mainbodylayout.getChildAt(i).setVisibility(View.GONE);
//        }
//    }


//fragment滑动事件

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        //i的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (i == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    clearStyle();//清空导航栏样式
                    setView(0);
                    break;
                case PAGE_TWO:
                    clearStyle();//清空导航栏样式
                    setView(1);
                    break;
                case PAGE_THREE:
                    clearStyle();//清空导航栏样式
                    setView(2);
                    break;
            }
        }
    }
}
