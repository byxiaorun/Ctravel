package com.byxiaorun.ctravel.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.byxiaorun.ctravel.R;
import com.byxiaorun.ctravel.Utils.DButils;
import com.byxiaorun.ctravel.bean.UserBean;
/**
 * Created by byxiaorun on 2020/1/4/.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_back;
    private EditText et_username,et_password,et_pswagin;
    private String username,psw,pswAgin;
    private Button btn_register;
    private DButils db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        tv_back=findViewById(R.id.tv_back);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
        btn_register=findViewById(R.id.btn_register);
        et_username=findViewById(R.id.et_user_name);
        et_password=findViewById(R.id.et_psw);
        et_pswagin=findViewById(R.id.et_psw_agin);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        getEditString();
        if (TextUtils.isEmpty(username)){
            Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(psw)){
            Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(pswAgin)){
            Toast.makeText(RegisterActivity.this,"请再次输入密码",Toast.LENGTH_SHORT).show();
            return;
        }else if (!psw.equals(pswAgin)){
            Toast.makeText(RegisterActivity.this,"输入的两次密码不一样哦",Toast.LENGTH_SHORT).show();
            return;
        }else if (DButils.checkusername(username)){
            Toast.makeText(RegisterActivity.this,"此用户名已存在",Toast.LENGTH_SHORT).show();
            return;
        }else {
            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
            saveRegisterInfo(username,psw);
            Intent data=new Intent();
            data.putExtra("userName",username);
            setResult(RESULT_OK,data);
            RegisterActivity.this.finish();
            return;
        }
    }


    private void saveRegisterInfo(String username, String psw) {
        UserBean bean=null;
        bean=new UserBean();
        bean.username=username;
        bean.password=psw;
        DButils.getInstance(this).saveUser(bean);
    }


    private void getEditString() {
        username=et_username.getText().toString().trim();
        psw=et_pswagin.getText().toString().trim();
        pswAgin=et_pswagin.getText().toString().trim();
    }

}
