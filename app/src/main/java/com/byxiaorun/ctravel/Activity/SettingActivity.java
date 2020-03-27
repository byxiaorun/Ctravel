package com.byxiaorun.ctravel.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.byxiaorun.ctravel.MainActivity;
import com.byxiaorun.ctravel.R;
import com.byxiaorun.ctravel.Utils.DButils;
/**
 * Created by byxiaorun on 2020/1/4/.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout tvmenu, main_bar;
    private TextView tv_back;
    private RelativeLayout rl_layout,editpassword;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    private void init() {
        tvmenu = findViewById(R.id.tv_menu);
        main_bar = findViewById(R.id.main_bar);
        tv_back = findViewById(R.id.tv_back);
        tvmenu.setVisibility(View.GONE);
        main_bar.setVisibility(View.GONE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.finish();
            }
        });
        rl_layout=findViewById(R.id.rl_layout);
        editpassword=findViewById(R.id.editpassword);
        editpassword.setOnClickListener(this);
        rl_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_layout:
                username= DButils.getUsername();
                DButils.updateLogin(username,"false");
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                break;
            case R.id.editpassword:
                Intent intent2 = new Intent(this, EditPasswordActivity.class);
                intent2.putExtra("option", "edit");
                this.startActivity(intent2);
                break;
                default:
                    break;
        }
    }
}
