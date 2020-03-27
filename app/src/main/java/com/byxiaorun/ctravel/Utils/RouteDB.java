package com.byxiaorun.ctravel.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.byxiaorun.ctravel.bean.RouteBean;
import com.byxiaorun.ctravel.sqlite.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byxiaorun on 2020/1/6/0006.
 */
public class RouteDB {
    private static RouteDB instance = null;
    private static SQLiteHelper helper;
    private static SQLiteDatabase db;

    public RouteDB(Context context) {
        helper = new SQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    public static RouteDB getInstance(Context context) {
        if (instance == null) {
            instance = new RouteDB(context);
        }
        return instance;
    }

    //查询路线
    public RouteBean select(int id) {
        boolean flag=false;
        RouteBean bean=null;
        String sql = "SELECT * FROM "+SQLiteHelper.ROUTE+" WHERE _id = ?";
        String ids= String.valueOf(id);
        Cursor cursor= db.rawQuery(sql, new String[]{ids});
        if (cursor.moveToNext()){
            bean=new RouteBean();
            bean.username=cursor.getString(cursor.getColumnIndex("username"));
            bean.city=cursor.getString(cursor.getColumnIndex("city"));
            bean.route=cursor.getString(cursor.getColumnIndex("route"));
            bean.reason=cursor.getString(cursor.getColumnIndex("reason"));
        }else {

        }
        cursor.close();
        return bean;
    }

    //插入路线
    public void saveRoute(String username, String city, String route, String reason) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("city", city);
        cv.put("route", route);
        cv.put("reason", reason);
        db.insert(SQLiteHelper.ROUTE, null, cv);
    }

    //获取路线列表
    public List<RouteBean> getList() {
        List<RouteBean> list = new ArrayList<RouteBean>();
        Cursor c = db.rawQuery("select * from " + SQLiteHelper.ROUTE, null);
        RouteBean bean = null;
        if (c.getCount() > 0) {   //如果查询到的行数大于0
            while (c.moveToNext()) {
                bean = new RouteBean();
                bean.id=c.getInt(c.getColumnIndex("_id"));
                bean.username = c.getString(c.getColumnIndex("username"));
                bean.city = c.getString(c.getColumnIndex("city"));
                bean.route = c.getString(c.getColumnIndex("route"));
                bean.reason = c.getString(c.getColumnIndex("reason"));
                list.add(bean);
                bean = null;
            }
        }
        c.close();
        return list;
    }


    public List<RouteBean> getmyList(String username) {
        List<RouteBean> list = new ArrayList<RouteBean>();
        Cursor c = db.rawQuery("select * from " + SQLiteHelper.ROUTE+" WHERE username=?", new String[]{username});
        RouteBean bean = null;
        if (c.getCount() > 0) {   //如果查询到的行数大于0
            while (c.moveToNext()) {
                bean = new RouteBean();
                bean.id=c.getInt(c.getColumnIndex("_id"));
                bean.username = c.getString(c.getColumnIndex("username"));
                bean.city = c.getString(c.getColumnIndex("city"));
                bean.route = c.getString(c.getColumnIndex("route"));
                bean.reason = c.getString(c.getColumnIndex("reason"));
                list.add(bean);
                bean = null;
            }
        }
        c.close();
        return list;
    }

    //我点赞的列表
    public List<RouteBean> getmyloveList(String username) {
        List<RouteBean> list = new ArrayList<RouteBean>();
        Cursor c= db.rawQuery("select routeid,route.route,route.city,route.username from love,route where love.routeid=route._id and love.username=?", new String[]{username});
        RouteBean bean = null;
        if (c.getCount() > 0) {   //如果查询到的行数大于0
            while (c.moveToNext()) {
                bean = new RouteBean();
                bean.id = Integer.parseInt(c.getString(c.getColumnIndex("routeid")));
                bean.username=c.getString(c.getColumnIndex("username"));
                bean.route=c.getString(c.getColumnIndex("route"));
                bean.city = c.getString(c.getColumnIndex("city"));
                list.add(bean);
                bean = null;
            }
        }
        c.close();
        return list;
    }
}
