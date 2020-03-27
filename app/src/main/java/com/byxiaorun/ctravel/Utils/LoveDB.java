package com.byxiaorun.ctravel.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.byxiaorun.ctravel.bean.LoveBean;
import com.byxiaorun.ctravel.bean.NoteBean;
import com.byxiaorun.ctravel.sqlite.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byxiaorun on 2020/1/10/0010.
 */
public class LoveDB {
    private static LoveDB instance = null;
    private static SQLiteHelper helper;
    private static SQLiteDatabase db;

    public LoveDB(Context context) {
        helper = new SQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    public static LoveDB getInstance(Context context) {
        if (instance == null) {
            instance = new LoveDB(context);
        }
        return instance;
    }

    //查询是否点赞
    public boolean selectlove(String username, int id, boolean name) {
        boolean flag = false;
        LoveBean bean = null;
        //如果name=true那么就查询游记是否点赞，false就查询路线是否点赞
        String sql = "";
        if (name) {
            sql = "SELECT * FROM " + SQLiteHelper.LOVE + " WHERE username = ? AND noteid=?";
        } else {
            sql = "SELECT * FROM " + SQLiteHelper.LOVE + " WHERE username = ? AND routeid=?";
        }
        String ids = String.valueOf(id);
        Cursor cursor = db.rawQuery(sql, new String[]{username, ids});
        if (cursor.moveToFirst()) {
            flag = true;
        } else {
            flag = false;
        }
        cursor.close();
        return flag;
    }

    //点赞的用户
    public List<LoveBean> getList(int id, boolean name) {
        List<LoveBean> list = new ArrayList<LoveBean>();
        Cursor c;
        if (name) {
            c = db.rawQuery("select * from " + SQLiteHelper.LOVE + " WHERE noteid=?", new String[]{String.valueOf(id)});
        } else {
            c = db.rawQuery("select * from " + SQLiteHelper.LOVE + " WHERE routeid=?", new String[]{String.valueOf(id)});
        }
        LoveBean bean = null;
        if (c.getCount() > 0) {   //如果查询到的行数大于0
            while (c.moveToNext()) {
                bean = new LoveBean();
                bean.username = c.getString(c.getColumnIndex("username"));
                bean.noteid = c.getString(c.getColumnIndex("noteid"));
                bean.routeid = c.getString(c.getColumnIndex("routeid"));
                list.add(bean);
                bean = null;
            }
        }
        c.close();
        return list;
    }

    //插入列表
    public void saveLove(String username, int id, boolean name) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        if (name) {
            cv.put("noteid", id);
        } else {
            cv.put("routeid", id);
        }
        db.insert(SQLiteHelper.LOVE, null, cv);
    }

    //删除点赞
    public boolean deletelove(String username, int id, boolean name) {
        boolean flag = false;
        LoveBean bean = null;
        //如果name=true那么就查询游记是否点赞，false就查询路线是否点赞
        String sql = "";
        if (name) {
            sql = "DELETE FROM " + SQLiteHelper.LOVE + " WHERE username = ? AND noteid=?";
        } else {
            sql = "DELETE FROM " + SQLiteHelper.LOVE + " WHERE username = ? AND routeid=?";
        }
        String ids = String.valueOf(id);
        Cursor cursor = db.rawQuery(sql, new String[]{username, ids});
        if (cursor.moveToFirst()) {
            flag = true;
        } else {
            flag = false;
        }
        cursor.close();
        return flag;
    }


}
