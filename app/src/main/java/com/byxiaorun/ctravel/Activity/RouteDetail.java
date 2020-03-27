package com.byxiaorun.ctravel.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.byxiaorun.ctravel.R;
import com.byxiaorun.ctravel.Utils.RouteDB;
import com.byxiaorun.ctravel.bean.RouteBean;

/**
 * Created by byxiaorun on 2020/1/6/0006.
 */
public class RouteDetail extends AppCompatActivity {
    TextView tv_city,tv_route,tv_reason,username,title,user;
    LinearLayout main_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_detail_item);
        init();
    }

    private void init() {
        username=findViewById(R.id.usernametext);
        user=findViewById(R.id.username);
        tv_city=findViewById(R.id.city);
        tv_route=findViewById(R.id.route);
        tv_reason=findViewById(R.id.reason);
        main_title=findViewById(R.id.main_title);
        title=findViewById(R.id.title);
        main_title.setVisibility(View.VISIBLE);
        user.setVisibility(View.GONE);
        title.setTextSize(30);
        title.setText("路线详情");
        Intent intent = getIntent();
        int id=intent.getIntExtra("id",-1);
        RouteDB db=RouteDB.getInstance(this);
        RouteBean bean=db.select(id);
        username.setText(String.valueOf(bean.username));
        tv_city.setText("城市:"+String.valueOf(bean.city));
        tv_route.setText("推荐路线:"+String.valueOf(bean.route));
        tv_reason.setText("推荐理由:"+String.valueOf(bean.reason));
    }
}
