package com.ittx.android1601.datastore.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ittx.android1601.logcat.Logs;

/**
 * SQLiteOpenHelper是SQLiteDatabase的一个帮助类，用来管理数据库的创建和版本的更新。
 * 一般是建立一个类继承它，并实现它的onCreate和onUpgrade方法。
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "android1601.db";
    private final static int DATABASE_VERSION = 5;

    /**
     * 初始化数据库
     * 数据库名称
     * 数据库版本号
     * @param context
     */
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Logs.e("DataBaseHelper 构造方法");
    }

    /**
     * 在数据库第一次创建时执行
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tablesql = "create table user(id Integer,name varchar(20),password varchar(6) ,PRIMARY KEY(id))";
        db.execSQL(tablesql);
        Logs.e("onCreate 方法");
    }

    /**
     * newVersion新版本号大于oldVersion旧版本号则执行onUpgrade方法
     * 执行完成oldVersion = newVersion;
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Logs.e("onUpgrade 方法 oldVersion: "+oldVersion + " ,newVersion:  "+newVersion);

        switch(oldVersion){
            case 3: {
                String sql = "Alter table user add column sex varchar(2)";
                db.execSQL(sql);
            }
            case 4: {
                String sql1 = "Alter table user add column number Integer";
                db.execSQL(sql1);
            }
        }

    }
}
