package com.ittx.android1601.datastore.sqlite;

import android.provider.BaseColumns;

/**
 * 封装数据库表字段
 */
public class DataColumn {
    /**
     * User用户表
     */
    public static class UserTable implements BaseColumns{
        public static final String TABLE_NAME_USER = "user";
        public static final String COLUMN_NAME_NAME = "name"; //姓名
        public static final String COLUMN_NAME_PASSWORD = "password"; //密码
        public static final String COLUMN_NAME_SEX = "sex"; //性别
        public static final String COLUMN_NAME_NUMBER = "number"; //编号
    }

    /**
     * Student学生表
     */
    public static class StudentTable{
        public static final String TABLE_NAME_SUTDENT = "student";
        public static final String COLUMN_NAME_ID = "_id"; //ID
        public static final String COLUMN_NAME_NAME = "name"; //姓名
        public static final String COLUMN_NAME_AGE = "age"; //年龄
    }

    /**
     * 音乐播放模式
     */
    public static class MusicPlayerModel{
        public static final int LOOPING_MODEL = 1;//单曲循环播放
        public static final int ORDERING_MODEL = 2;//顺序播放
        public static final int ALL_LOOPING_MODEL = 3;//全部循环播放
        public static final int RANDOM_MODEL = 4;//随机播放
    }
}
