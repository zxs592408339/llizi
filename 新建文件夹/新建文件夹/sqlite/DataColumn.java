package com.ittx.android1601.datastore.sqlite;

public class DataColumn {
    /**
     * User用户表字段定义
     */
    public static class UserTable{
        public static final String TABLE_NAME_USER = "user";
        public static final String COLUMN_NAME_ID = "_id"; //ID
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

}
