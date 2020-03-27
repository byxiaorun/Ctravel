package com.byxiaorun.ctravel.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by byxiaorun on 2020/1/5
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION=2;
    public static String DB_NAME="ctravel.db";
    public static final String USER="user";//用户列表
    public static final String USERInfo="userinfo";//用户资料
    public static final String NOTE="note";//游记表
    public static final String ROUTE="route";//路线推荐表
    public static final String LOVE="love";//喜欢列表
    public SQLiteHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //账号信息表
        db.execSQL("CREATE TABLE IF NOT EXISTS " + USER +"( "
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"username VARCHAR, "//用户名
                +"password VARCHAR, "//密码
                +"isLogin VARCHAR"//是否登录
                 +")"
        );
        //个人资料表
        db.execSQL("CREATE TABLE IF NOT EXISTS " + USERInfo +"( "
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"username VARCHAR, "//用户名
                +"nickname VARCHAR, "//昵称
                +"sex VARCHAR, "//性别
                +"signature VARCHAR"//签名
                +")"
        );
        //游记
        db.execSQL("CREATE TABLE IF NOT EXISTS " + NOTE +"( "
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"username VARCHAR, "//用户名
                +"note VARCHAR,"//游记内容
                +"time VARCHAR"//游记写入时间
                +")"
        );
        //路线推荐
        db.execSQL("CREATE TABLE IF NOT EXISTS " + ROUTE +"( "
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"username VARCHAR, "//用户名
                +"city VARCHAR, "//城市
                +"route VARCHAR, "//路线
                +"reason VARCHAR"//推荐理由
                +")"
        );
        //喜欢列表
        db.execSQL("CREATE TABLE IF NOT EXISTS " + LOVE +"( "
                +"username VARCHAR, "//用户名
                +"routeid VARCHAR, "//路线id
                +"noteid VARCHAR "//游记id
                +")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER);
        db.execSQL("DROP TABLE IF EXISTS " + USERInfo);
        db.execSQL("DROP TABLE IF EXISTS " + ROUTE);
        db.execSQL("DROP TABLE IF EXISTS " + NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + LOVE);
        onCreate(db);
    }
}
