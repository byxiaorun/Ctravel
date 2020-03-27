package com.byxiaorun.ctravel.Fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.byxiaorun.ctravel.Activity.LoginActivity;
import com.byxiaorun.ctravel.Activity.Love;
import com.byxiaorun.ctravel.Activity.Mynote;
import com.byxiaorun.ctravel.Activity.Myroute;
import com.byxiaorun.ctravel.Activity.SettingActivity;
import com.byxiaorun.ctravel.R;
import com.byxiaorun.ctravel.Utils.DButils;

/**
 * Created by byxiaorun on 2020/1/5
 */
public class MenuLeftFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Button login_button;
    private TextView tv_username;
    private LinearLayout signin, signout;
    private RelativeLayout setting, comment, lovenote,loveroute, note,myroute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_menu_left_fragment, container, false);
        init();
        setLoginParams(readLoginStatus());
        return view;
    }


    private void init() {
        login_button = view.findViewById(R.id.btn_login);
        tv_username = view.findViewById(R.id.username);
        signin = view.findViewById(R.id.signin);
        signout = view.findViewById(R.id.signout);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                getContext().startActivity(intent);
            }
        });
        setting = view.findViewById(R.id.rl_setting);
        note = view.findViewById(R.id.note);
        lovenote = view.findViewById(R.id.lovenote);
        loveroute = view.findViewById(R.id.loverount);
        myroute=view.findViewById(R.id.myroute);
        comment = view.findViewById(R.id.comment);
        setting.setOnClickListener(this);
        note.setOnClickListener(this);
        lovenote.setOnClickListener(this);
        loveroute.setOnClickListener(this);
        comment.setOnClickListener(this);
        signin.setOnClickListener(this);
        myroute.setOnClickListener(this);
    }

    public void setLoginParams(boolean isLogin) {
        if (isLogin) {
            signout.setVisibility(View.GONE);
            signin.setVisibility(View.VISIBLE);
            tv_username.setText(DButils.getUsername());
        } else {
            signout.setVisibility(View.VISIBLE);
            signin.setVisibility(View.GONE);
        }
    }

    private boolean readLoginStatus() {
        return DButils.getIsLogin();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin:
                if (readLoginStatus()) {
                } else {
                }
                break;
            case R.id.rl_setting:
                if (readLoginStatus()) {
                    Intent intent = new Intent(getContext(), SettingActivity.class);
                    getContext().startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "你还未登录,请先登陆", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lovenote:
                if (readLoginStatus()) {
                    Intent intent = new Intent(view.getContext(), Love.class);
                    intent.putExtra("lovename", "note");
                    view.getContext().startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "你还未登录,请先登陆", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.loverount:
                if (readLoginStatus()) {
                    Intent intent = new Intent(view.getContext(), Love.class);
                    intent.putExtra("lovename", "route");
                    view.getContext().startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "你还未登录,请先登陆", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.note:
                if (readLoginStatus()) {
                    Intent intent = new Intent(getContext(), Mynote.class);
                    getContext().startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "你还未登录,请先登陆", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.myroute:
                if (readLoginStatus()) {
                    Intent intent = new Intent(getContext(), Myroute.class);
                    getContext().startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "你还未登录,请先登陆", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.comment:
                if (readLoginStatus()) {

                } else {
                    Toast.makeText(view.getContext(), "你还未登录,请先登陆", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
