package com.byxiaorun.ctravel.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.byxiaorun.ctravel.Adapter.NoteAdapter;
import com.byxiaorun.ctravel.Adapter.RouteAdapter;
import com.byxiaorun.ctravel.R;
import com.byxiaorun.ctravel.Utils.DButils;
import com.byxiaorun.ctravel.Utils.NoteDB;
import com.byxiaorun.ctravel.Utils.RouteDB;
import com.byxiaorun.ctravel.bean.RouteBean;

import java.util.List;

/**
 * Created by byxiaorun on 2020/1/7/0007.
 */
public class Myroute extends AppCompatActivity {
    ListView lv;
    private List<RouteBean> list;
    private RouteDB db;
    private RouteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_route);
        lv=findViewById(R.id.lv_route);
        //从数据库中获取路线信息
        db= RouteDB.getInstance(this);
        list=db.getmyList(DButils.getUsername());
        adapter=new RouteAdapter(this,list,db);
        lv.setAdapter(adapter);
    }
}
