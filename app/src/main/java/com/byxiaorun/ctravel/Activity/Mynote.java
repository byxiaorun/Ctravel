package com.byxiaorun.ctravel.Activity;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.byxiaorun.ctravel.Adapter.NoteAdapter;
import com.byxiaorun.ctravel.R;
import com.byxiaorun.ctravel.Utils.DButils;
import com.byxiaorun.ctravel.Utils.NoteDB;
import com.byxiaorun.ctravel.bean.NoteBean;

import java.util.List;

/**
 * Created by byxiaorun on 2020/1/7/0007.
 */
public class Mynote extends AppCompatActivity {
    ListView lv;
    private List<NoteBean> list;
    private NoteDB db;
    private NoteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_route);
        lv=findViewById(R.id.lv_route);
        //从数据库中获取路线信息
        db= NoteDB.getInstance(this);
        list=db.getmyList(DButils.getUsername());
        adapter=new NoteAdapter(this,list,db);
        lv.setAdapter(adapter);
    }
}
