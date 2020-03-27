package com.byxiaorun.ctravel.Activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.byxiaorun.ctravel.Adapter.NoteAdapter;
import com.byxiaorun.ctravel.Fragment.FragmentAdapter;
import com.byxiaorun.ctravel.Fragment.HomeFragment;
import com.byxiaorun.ctravel.MainActivity;
import com.byxiaorun.ctravel.R;
import com.byxiaorun.ctravel.Utils.DButils;
import com.byxiaorun.ctravel.Utils.NoteDB;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by byxiaorun on 2020/1/6/0006.
 */
public class NoteActivity extends AppCompatActivity {
    private LinearLayout main_title;
    private TextView tv_main_title, username, tv_send;
    private EditText et_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        init();
    }

    private void init() {
        tv_main_title = findViewById(R.id.title);
        main_title = findViewById(R.id.main_title);
        username = findViewById(R.id.username);
        tv_send = findViewById(R.id.tv_send);
        et_note = findViewById(R.id.et_note);
        tv_send.setVisibility(View.VISIBLE);
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) titlebar.getLayoutParams(); ;
//        params.height=70;
//        titlebar.getLayoutParams();
//        titlebar.setLayoutParams(params);
        main_title.setVisibility(View.VISIBLE);
        if (DButils.getUsername() != null) {
            username.setText(DButils.getUsername());
        }
        Intent intent = getIntent();
        String name = intent.getStringExtra("option");
        if (name.equals("send")) {
            tv_main_title.setText("发表动态");
            tv_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String note = et_note.getText().toString();
                    if (TextUtils.isEmpty(note)) {
                        Toast.makeText(NoteActivity.this, "游记内容不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        String username = DButils.getUsername();
                        /*获取当前系统时间*/
//                        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
//                        df.setTimeZone(TimeZone.getTimeZone("GMT+08"));
//                        String nowtime = df.format(new Date());
                        String nowtime=null;
                        try {
                            DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                            df.setTimeZone(TimeZone.getTimeZone("GMT+08"));
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(System.currentTimeMillis());
                            nowtime = df.format(calendar.getTime());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        NoteDB db = NoteDB.getInstance(view.getContext());
                        db.saveNote(username, note, nowtime);
                        MainActivity.mAdapter.setnote();
                        NoteActivity.this.finish();
                        Toast.makeText(view.getContext(), "发表成功", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else if (name.equals("edit")) {
            tv_main_title.setText("编辑动态");
            String note = intent.getStringExtra("note");
            String id = intent.getStringExtra("id");
            et_note.setText(note);
            tv_send.setText("保存");
            tv_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String note = et_note.getText().toString();
                    if (TextUtils.isEmpty(note)) {
                        Toast.makeText(NoteActivity.this, "游记内容不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        NoteDB db = NoteDB.getInstance(view.getContext());
                        boolean flag = db.updateNote(id, note);
                        if (flag) {
                            MainActivity.mAdapter.setnote();
                            NoteActivity.this.finish();
                            Toast.makeText(view.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}
