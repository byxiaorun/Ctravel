package com.byxiaorun.ctravel.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.byxiaorun.ctravel.bean.UserBean;
import com.byxiaorun.ctravel.sqlite.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

/**
 * Created by byxiaorun on 2020/1/5/
 */
public class DButils {
    private static DButils instance = null;
    private static SQLiteHelper helper;
    private static SQLiteDatabase db;

    public DButils(Context context) {
        helper = new SQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    public static DButils getInstance(Context context) {
        if (instance == null) {
            instance = new DButils(context);
        }
        return instance;
    }

    //检查用户名是否重复
    public static boolean checkusername(String username) {
        boolean flag = false;
        String sql = "SELECT * FROM " + SQLiteHelper.USER + " WHERE username = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        if (cursor.moveToFirst()) {
            flag = true;
        }
        cursor.close();
        return flag;
    }

    //保存账号密码信息
    public void saveUser(UserBean bean) {
        ContentValues cv = new ContentValues();
        cv.put("username", bean.username);
        cv.put("password", bean.password);
        db.insert(SQLiteHelper.USER, null, cv);
    }

    //判断密码是否正确
    public static boolean checkpassword(String username, String password) {
        boolean flag = false;
        String sql = "SELECT * FROM " + SQLiteHelper.USER + " WHERE username = ? AND password=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username, password});
        if (cursor.moveToFirst()) {
            flag = true;
        }
        cursor.close();
        return flag;
    }

    //更新登录信息
    public static void updateLogin(String username, String islogin) {
        String sql = "UPDATE " + SQLiteHelper.USER + " SET isLogin = ? WHERE  username=?";
        Cursor cursor = db.rawQuery(sql, new String[]{islogin, username});
        if (cursor.moveToFirst()) {
            Log.e("用户登录状态更新失败", "用户登录状态更新失败");
        } else {
            Log.e("用户登录状态更新成功", "用户登录状态更新成功");
        }
        cursor.close();
    }

    //获取已登录的用户名
    public static String getUsername() {
        String sql = "SELECT * FROM " + SQLiteHelper.USER + " WHERE islogin=?";
        String yes = "true";
        String username = "";
        Cursor cursor = db.rawQuery(sql, new String[]{yes});
        if (cursor.moveToNext()) {
            username = cursor.getString(cursor.getColumnIndex("username"));
        }
        cursor.close();
        return username;
    }

    //获取登录状态
    public static boolean getIsLogin() {
        boolean flag = false;
        String sql = "SELECT * FROM " + SQLiteHelper.USER + " WHERE islogin = ?";
        String yes = "true";
        Cursor cursor = db.rawQuery(sql, new String[]{yes});
        if (cursor.moveToFirst()) {
            flag = true;
        } else {

        }
        cursor.close();
        return flag;
    }

    //修改密码
    public static boolean edit(String username, String passowrd, String newpassword) {
        boolean flag=false;
        if (checkpassword(username, passowrd)) {
            String sql = "UPDATE " + SQLiteHelper.USER + " SET password = ? WHERE  username=?";
            Cursor cursor = db.rawQuery(sql, new String[]{newpassword, username});
            if (cursor.moveToFirst()) {
                Log.e("密码修改失败", "密码修改失败");
                flag=false;
            } else {
                Log.e("密码修改成功", "密码修改成功");
                flag=true;
            }
            cursor.close();
        }else {
            flag=false;
        }
        return flag;
    }

}
