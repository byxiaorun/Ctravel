package com.byxiaorun.ctravel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.byxiaorun.ctravel.Adapter.NoteAdapter;
import com.byxiaorun.ctravel.Adapter.RouteAdapter;
import com.byxiaorun.ctravel.R;
import com.byxiaorun.ctravel.Utils.DButils;
import com.byxiaorun.ctravel.Utils.LoveDB;
import com.byxiaorun.ctravel.Utils.NoteDB;
import com.byxiaorun.ctravel.Utils.RouteDB;
import com.byxiaorun.ctravel.bean.LoveBean;
import com.byxiaorun.ctravel.bean.NoteBean;
import com.byxiaorun.ctravel.bean.RouteBean;

import java.util.List;

/**
 * Created by byxiaorun on 2020/1/10/0010.
 */
public class Love extends AppCompatActivity {
    ListView lv;
    private List<NoteBean> notelist;
    private List<RouteBean> routelist;
    private NoteDB noteDB;
    private RouteDB routeDB;
    private NoteAdapter noteadapter;
    private RouteAdapter routeAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String name= intent.getStringExtra("lovename");
        setContentView(R.layout.fragment_route);
        lv=findViewById(R.id.lv_route);
        if (name.equals("note")) {
            noteDB= NoteDB.getInstance(this);
            notelist=noteDB.getmyloveList(DButils.getUsername());
            noteadapter=new NoteAdapter(this,notelist,noteDB);
            lv.setAdapter(noteadapter);
        }else if (name.equals("")){
            routeDB=RouteDB.getInstance(this);
            routelist=routeDB.getmyloveList(DButils.getUsername());
            routeAdapter=new RouteAdapter(this,routelist,routeDB);
            lv.setAdapter(routeAdapter);
        }

    }
}
