package com.byxiaorun.ctravel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.byxiaorun.ctravel.Activity.LoginActivity;
import com.byxiaorun.ctravel.Activity.RegisterActivity;
import com.byxiaorun.ctravel.Activity.RouteDetail;
import com.byxiaorun.ctravel.MainActivity;
import com.byxiaorun.ctravel.R;
import com.byxiaorun.ctravel.Utils.DButils;
import com.byxiaorun.ctravel.Utils.LoveDB;
import com.byxiaorun.ctravel.Utils.RouteDB;
import com.byxiaorun.ctravel.bean.RouteBean;

import java.util.List;

/**
 * Created by byxiaorun on 2020/1/6/0006.
 */
public class RouteAdapter extends BaseAdapter{
    private Context context;
    private List<RouteBean> routelist;
    private LayoutInflater inflater;
    private RouteDB routedb;
    public RouteAdapter(Context context, List<RouteBean> routelist, RouteDB routedb) {
        this.context = context;
        this.routelist = routelist;
        this.inflater = inflater;
        this.routedb = routedb;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return routelist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Holder holder=null;
        if (view==null){
            holder=new Holder();
            view=inflater.inflate(R.layout.route_list_item,null);
            holder.id=view.findViewById(R.id.tv_id);
            holder.ll_route=view.findViewById(R.id.ll_route);
            holder.username=view.findViewById(R.id.tv_username);
            holder.city=view.findViewById(R.id.tv_city);
            holder.route=view.findViewById(R.id.tv_route);
            holder.good=view.findViewById(R.id.good);
            view.setTag(holder);
        }else {
            holder= (Holder) view.getTag();
        }
        final RouteBean route=routelist.get(i);
        holder.id.setText(new String(String.valueOf(route.id)));
        holder.username.setText(route.username);
        holder.city.setText(route.city);
        holder.route.setText(route.route);
        int id=route.id;
        LoveDB db = LoveDB.getInstance(view.getContext());

        good(db, id, holder, view);
        //获取点赞列表数目
        holder.good.setText(String.valueOf(db.getList(id, false).size()));
        holder.ll_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouteDetail routeDetail = null;
                Intent intent = new Intent(context, RouteDetail.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });

        return view;
    }

    private void good(LoveDB db, int id, Holder holder, View view) {
        //点赞及取消点赞操作
        if (DButils.getIsLogin()) {
            if (db.selectlove(DButils.getUsername(), id, false)) {
                //如果查询到点赞
                holder.good.setSelected(true);
                holder.good.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.deletelove(DButils.getUsername(), id, false);
                        MainActivity.mAdapter.setroute();
                        good(db,id,holder,view);
                        holder.id.setText(new String(String.valueOf(id)));
                        Toast.makeText(view.getContext(), "取消推荐成功", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                holder.good.setSelected(false);
                holder.good.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.saveLove(DButils.getUsername(), id, false);
                        MainActivity.mAdapter.setroute();
                        good(db,id,holder,view);
                        holder.id.setText(new String(String.valueOf(id)));
                        Toast.makeText(view.getContext(), "推荐成功", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        } else {
            Toast.makeText(view.getContext(), "你还未登录,请先登陆", Toast.LENGTH_SHORT).show();
        }
    }

    class Holder{
        LinearLayout ll_route;
        TextView id,username,city,route;
        Button good;
    }
    public void setlist(List<RouteBean> list){
        this.routelist=list;
    }
}
