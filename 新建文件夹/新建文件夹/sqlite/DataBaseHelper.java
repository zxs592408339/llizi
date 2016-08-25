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
    private final static String DATABASE_NAME = "android1601.db"; //数据库名
    private final static int DATABASE_VERSION = 3; //数据库版本号
    //创建User用户名SQL语句
    private static String createUserTabelSQL = "create table " + DataColumn.UserTable.TABLE_NAME_USER
            + "("
            + DataColumn.UserTable.COLUMN_NAME_ID + " Integer NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + DataColumn.UserTable.COLUMN_NAME_NAME + " varchar(20),"
            + DataColumn.UserTable.COLUMN_NAME_PASSWORD + " varchar(6) "
            + ")";
    //添加性别字段sex到User表
    private static String alterUserAddSexSQL = "Alter table " + DataColumn.UserTable.TABLE_NAME_USER
            + " add column " + DataColumn.UserTable.COLUMN_NAME_SEX + " varchar(2)";
    //添加编号字段number到User表
    private static String alterUserAddNumberSQL = "Alter table " + DataColumn.UserTable.TABLE_NAME_USER
            + " add column " + DataColumn.UserTable.COLUMN_NAME_NUMBER + " Integer";

    private static String createStudentSQL = "CREATE TABLE "+ DataColumn.StudentTable.TABLE_NAME_SUTDENT
            + "("
            + DataColumn.StudentTable.COLUMN_NAME_ID + " Integer NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + DataColumn.StudentTable.COLUMN_NAME_NAME + " varchar(50), "
            + DataColumn.StudentTable.COLUMN_NAME_AGE + " varchar(3) "
            +")";

    /**
     * 初始化数据库
     * 数据库名称
     * 数据库版本号
     *
     * @param context
     */
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Logs.e("DataBaseHelper 构造方法");
    }

    /**
     * 在数据库第一次创建时执行
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUserTabelSQL);
        Logs.e("onCreate 方法");
    }

    /**
     * 版本号发生变化时执行
     * newVersion新版本号必须大于oldVersion旧版本号
     * 执行完成oldVersion = newVersion;
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Logs.e("onUpgrade 方法 oldVersion: " + oldVersion + " ,newVersion:  " + newVersion);

        switch (oldVersion) {
            case 1: //为数据库旧版本号为1应用添加一个性别sex字段
                db.execSQL(alterUserAddSexSQL);
            case 2: //为数据库旧版本号为1应用添加一个编号number字段并且新增一个学生表
                db.execSQL(alterUserAddNumberSQL);
                db.execSQL(createStudentSQL);
        }

    }
}
