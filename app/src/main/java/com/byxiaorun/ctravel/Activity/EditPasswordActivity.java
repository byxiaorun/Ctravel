package com.byxiaorun.ctravel.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.byxiaorun.ctravel.MainActivity;
import com.byxiaorun.ctravel.R;
import com.byxiaorun.ctravel.Utils.DButils;

public class EditPasswordActivity extends AppCompatActivity {
    private TextView tv_back, tv_main_title;
    private Button btn_edit;
    private EditText et_psw, et_newpsw, et_newpsw_again;
    private String psw, newpsw, newpswagain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        init();
        setListener();//监听事件
    }

    private void init() {
        tv_back = findViewById(R.id.tv_back);
        tv_back.setVisibility(View.VISIBLE);
        tv_main_title = findViewById(R.id.tv_main_title);
        et_psw = findViewById(R.id.et_password);
        et_newpsw = findViewById(R.id.et_newpw);
        et_newpsw_again = findViewById(R.id.et_newpw_again);
        btn_edit = findViewById(R.id.btn_edit);
        Intent intent = getIntent();
        String name = intent.getStringExtra("option");
        if (name.equals("edit")) {
            tv_main_title.setText("修改密码");
            btn_edit.setText("修改密码");
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    psw = et_psw.getText().toString().trim();
                    newpsw = et_newpsw.getText().toString().trim();
                    newpswagain = et_newpsw_again.getText().toString().trim();
                    if (!newpsw.equals(newpswagain)) {
                        Toast.makeText(EditPasswordActivity.this, "输入的两次密码不一样哦", Toast.LENGTH_SHORT).show();
                        return;
                    }else if (DButils.checkpassword(DButils.getUsername(),psw)==false){
                        Toast.makeText(EditPasswordActivity.this, "原密码不正确", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        DButils.edit(DButils.getUsername(),psw,newpsw);
                        DButils.updateLogin(DButils.getUsername(),"false");
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        view.getContext().startActivity(intent);
                        Toast.makeText(view.getContext(), "修改成功,已为你自动退出登录", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setListener() {
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditPasswordActivity.this.finish();
            }
        });

    }
}
