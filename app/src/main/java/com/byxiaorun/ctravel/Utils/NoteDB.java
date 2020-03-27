package com.byxiaorun.ctravel.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.byxiaorun.ctravel.bean.NoteBean;
import com.byxiaorun.ctravel.sqlite.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byxiaorun on 2020/1/6/0006.
 */
public class NoteDB {
    private static NoteDB instance = null;
    private static SQLiteHelper helper;
    private static SQLiteDatabase db;

    public NoteDB(Context context) {
        helper = new SQLiteHelper(context);
        db = helper.getWritableDatabase();
    }
    public static NoteDB getInstance(Context context) {
        if (instance == null) {
            instance = new NoteDB(context);
        }
        return instance;
    }
    //查询游记
    public NoteBean select(int id) {
        boolean flag=false;
        NoteBean bean=null;
        String sql = "SELECT * FROM "+SQLiteHelper.NOTE+" WHERE _id = ?";
        String ids= String.valueOf(id);
        Cursor cursor= db.rawQuery(sql, new String[]{ids});
        if (cursor.moveToNext()){
            bean=new NoteBean();
            bean.username=cursor.getString(cursor.getColumnIndex("username"));
            bean.time=cursor.getString(cursor.getColumnIndex("time"));
            bean.note=cursor.getString(cursor.getColumnIndex("note"));
        }else {

        }
        cursor.close();
        return bean;
    }


    //保存动态
    public void saveNote(String username, String note, String time) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("note", note);
        cv.put("time", time);
        db.insert(SQLiteHelper.NOTE, null, cv);
    }

    //修改动态
    public boolean updateNote(String id, String note) {
        boolean flag=false;
        String sql = "UPDATE "+SQLiteHelper.NOTE+" SET note = ? WHERE  _id=?";
        Cursor cursor= db.rawQuery(sql, new String[]{note,id});
        if (cursor.moveToFirst()){
            flag=false;
        }else {
            flag=true;
        }
        cursor.close();
        return flag;
    }
    //删除动态
    public boolean deletenote(String id) {
        boolean flag=false;
        String sql = "DELETE FROM  "+SQLiteHelper.NOTE+" WHERE  _id=?";
        Cursor cursor= db.rawQuery(sql, new String[]{id});
        if (cursor.moveToFirst()){
            flag=false;
        }else {
            flag=true;
        }
        cursor.close();
        return flag;
    }


    public List<NoteBean> getList() {
        List<NoteBean> list = new ArrayList<NoteBean>();
        Cursor c = db.rawQuery("select * from " + SQLiteHelper.NOTE +" order by time desc", null);
        NoteBean bean = null;
        if (c.getCount() > 0) {   //如果查询到的行数大于0
            while (c.moveToNext()) {
                bean = new NoteBean();
                bean.id=c.getInt(c.getColumnIndex("_id"));
                bean.username = c.getString(c.getColumnIndex("username"));
                bean.note = c.getString(c.getColumnIndex("note"));
                bean.time = c.getString(c.getColumnIndex("time"));
                list.add(bean);
                bean = null;
            }
        }
        c.close();
        return list;
    }

    public List<NoteBean> getmyList(String username) {
        List<NoteBean> list = new ArrayList<NoteBean>();
        Cursor c = db.rawQuery("select * from " + SQLiteHelper.NOTE +" WHERE username=? order by time desc", new String[]{username});
        NoteBean bean = null;
        if (c.getCount() > 0) {   //如果查询到的行数大于0
            while (c.moveToNext()) {
                bean = new NoteBean();
                bean.id=c.getInt(c.getColumnIndex("_id"));
                bean.username = c.getString(c.getColumnIndex("username"));
                bean.note = c.getString(c.getColumnIndex("note"));
                bean.time = c.getString(c.getColumnIndex("time"));
                list.add(bean);
                bean = null;
            }
        }
        c.close();
        return list;
    }
    //我点赞的列表
    public List<NoteBean> getmyloveList(String username) {
        List<NoteBean> list = new ArrayList<NoteBean>();
        //Cursor c= db.rawQuery("select * from "+SQLiteHelper.LOVE+" where username=? and noteid is not null", new String[]{username});
        Cursor c= db.rawQuery("select noteid,note.note,note.time,note.username from love,note where love.noteid=note._id and love.username=?", new String[]{username});
        NoteBean bean = null;
        //NoteBean bean2 = null;
        if (c.getCount() > 0) {   //如果查询到的行数大于0
            while (c.moveToNext()) {
                bean = new NoteBean();
                bean.id = Integer.parseInt(c.getString(c.getColumnIndex("noteid")));
                //bean2=new NoteBean();
                //bean2=select(bean.id);
//                bean.username=bean2.username;
//                bean.note=bean2.note;
//                bean.time=bean2.time;
                bean.username=c.getString(c.getColumnIndex("username"));
                bean.note=c.getString(c.getColumnIndex("note"));
                bean.time=c.getString(c.getColumnIndex("time"));
                list.add(bean);
                bean = null;
            }
        }
        c.close();
        return list;
    }


}
