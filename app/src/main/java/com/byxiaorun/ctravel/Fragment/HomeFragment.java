package com.byxiaorun.ctravel.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.byxiaorun.ctravel.Activity.NoteActivity;
import com.byxiaorun.ctravel.Activity.LoginActivity;
import com.byxiaorun.ctravel.Adapter.NoteAdapter;
import com.byxiaorun.ctravel.R;
import com.byxiaorun.ctravel.Utils.DButils;
import com.byxiaorun.ctravel.Utils.NoteDB;
import com.byxiaorun.ctravel.bean.NoteBean;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * Created by byxiaorun on 2020/1/4/
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ListView lv;
    private List<NoteBean> list;
    private NoteDB db;
    private NoteAdapter adapter;
    private FloatingActionButton fb_send;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        //从数据库中获取路线信息
        db= NoteDB.getInstance(getContext());
        list=db.getList();
        adapter=new NoteAdapter(view.getContext(),list,db);
        init();
        return view;
    }

    private void init() {
        fb_send = view.findViewById(R.id.fb_send);
        lv = view.findViewById(R.id.lv);
        lv.setAdapter(adapter);
        fb_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_send:
                if (DButils.getIsLogin()) {
                    Intent intent = new Intent(getContext(), NoteActivity.class);
                    intent.putExtra("option", "send");
                    getContext().startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "你还未登录,请先登陆", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    getContext().startActivity(intent);
                }
        }
    }
    public void re()
    {
        list=db.getList();
        adapter.setlist(list);
        if (adapter!=null) {
            adapter.notifyDataSetChanged();
        }
    }
}
