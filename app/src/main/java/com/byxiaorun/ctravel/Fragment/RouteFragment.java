package com.byxiaorun.ctravel.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.byxiaorun.ctravel.Adapter.RouteAdapter;
import com.byxiaorun.ctravel.R;
import com.byxiaorun.ctravel.Utils.RouteDB;
import com.byxiaorun.ctravel.bean.RouteBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byxiaorun on 2020/1/6
 */
public class RouteFragment extends Fragment {
    private ListView lv;
    private List<RouteBean> list;
    private RouteDB db;
    private RouteAdapter adapter;
    View view;
    public RouteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_route, container, false);
        db= RouteDB.getInstance(getContext());
        //从数据库中获取路线信息
        list=db.getList();
        adapter=new RouteAdapter(view.getContext(),list,db);
        init();
        return view;
    }

    private void init() {
        lv=view.findViewById(R.id.lv_route);
        lv.setAdapter(adapter);
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
