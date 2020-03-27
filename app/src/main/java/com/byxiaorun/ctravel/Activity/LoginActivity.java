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

import com.byxiaorun.ctravel.MainActivity;
import com.byxiaorun.ctravel.R;
import com.byxiaorun.ctravel.Utils.DButils;
/**
 * Created by byxiaorun on 2020/1/4/.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_back;
    private TextView tv_reg;
    private Button btn_login;
    private EditText et_user_name, et_psw;
    private String userName, psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        setListener();//监听事件
    }

    private void init() {
        tv_back = findViewById(R.id.tv_back);
        tv_back.setVisibility(View.VISIBLE);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_reg = findViewById(R.id.tv_register);
        et_user_name = findViewById(R.id.et_user_name);
        et_psw = findViewById(R.id.et_psw);
    }

    private void setListener() {
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });
        tv_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        userName = et_user_name.getText().toString().trim();
        psw = et_psw.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(psw)) {
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        } else if (DButils.checkpassword(userName,psw)) {
            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            //保持登录状态和登录的用户名
            DButils.updateLogin(userName, "true");
            Intent data = new Intent();
            data.putExtra("isLogin", true);
            setResult(RESULT_OK, data);
            LoginActivity.this.finish();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivityForResult(intent, 1);
            return;
        } else if (DButils.checkpassword(userName,psw)==false){
            Toast.makeText(LoginActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(LoginActivity.this, "此用户不存在", Toast.LENGTH_SHORT).show();
        }
    }
}
