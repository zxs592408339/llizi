package com.ittx.android1601.datastore.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.ittx.android1601.datastore.sqlite.DataBaseHelper;
import com.ittx.android1601.datastore.sqlite.DataColumn;
import com.ittx.android1601.logcat.Logs;

/**
 * ContentProvider类提供了一些标准方法接口,允许自身或其它应用程序访问,
 * 标准方法接口自己实现
 * // content://com.ittx.android1601.datastore.provider.MyContentProvider/user
 * 在上边两个类中用到的Uri一般由3部分组成:scheme, authority and path
 *   第一部分是方案："content://" 这部分永远不变
 *   第二部分是授权："contacts"
 *   第三部分是路径："people/","people/1"（如果没有指定ID，那么表示返回全部）。
 * <p/>
 * content://com.android.contacts/peopel
 * \------/\--------------------/\--/
 * scheme      authority    path
 *               
 */
public class MyContentProvider extends ContentProvider {
    private static final String USER_PATH = "user";
    private static final String STUDENT_PATH = "student";
    private static final int USER_CODE = 0x0000;
    private static final int USER_ID_CODE = USER_CODE + 1;

    private static final int STUDENT_CODE = 0x1000;
    private static final int STUDENT_ID_CODE = STUDENT_CODE + 1;

    private static final String SCHEME = "content://";
    private static final String AUTHORITY = "com.ittx.android1601.datastore.provider.MyContentProvider";

    private static final String CONTENT_URI = SCHEME + AUTHORITY;
    public static final String CONTENT_URI_STUDENT = CONTENT_URI + "/" + STUDENT_PATH;
    public static final String CONTENT_URI_USER = CONTENT_URI + "/" + USER_PATH;

    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDB;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, USER_PATH, USER_CODE);
        uriMatcher.addURI(AUTHORITY, USER_PATH + "/#/#", USER_ID_CODE);
        uriMatcher.addURI(AUTHORITY, STUDENT_PATH, STUDENT_CODE);
        uriMatcher.addURI(AUTHORITY, STUDENT_PATH + "/#", STUDENT_ID_CODE);
    }

    @Override
    public boolean onCreate() {
        Logs.e("MyContentProvider onCreate >>>>> ");
        mDataBaseHelper = new DataBaseHelper(getContext());
        mDB = mDataBaseHelper.getReadableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int code = uriMatcher.match(uri);
        switch (code) {
            case USER_CODE:
                return mDB.query(DataColumn.UserTable.TABLE_NAME_USER, projection, selection, selectionArgs, null, null, sortOrder);
            case USER_ID_CODE:
                String id = uri.getPathSegments().get(1);
                for(String i : uri.getPathSegments()){
                    Logs.e("i :"+i);
                }

                Logs.e("id >>>>:" + id);
                return mDB.query(DataColumn.UserTable.TABLE_NAME_USER, projection, whereWithId(id, selection), selectionArgs, null, null, sortOrder);
            case STUDENT_CODE:
                return mDB.query(DataColumn.StudentTable.TABLE_NAME_SUTDENT, projection, selection, selectionArgs, null, null, sortOrder);

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // content://com.ittx.android1601.datastore.provider.MyContentProvider/user/
        // content://com.ittx.android1601.datastore.provider.MyContentProvider/student
        int code = uriMatcher.match(uri);
        long id = 0;
        switch (code) {
            case USER_CODE:
                id = mDB.insert(DataColumn.UserTable.TABLE_NAME_USER, null, values);
                break;
            case STUDENT_CODE:
                id = mDB.insert(DataColumn.StudentTable.TABLE_NAME_SUTDENT, null, values);
        }

        return ContentUris.withAppendedId(uri, id); //content://com.ittx.android1601.datastore.provider.MyContentProvider/user/1
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return mDB.delete(DataColumn.UserTable.TABLE_NAME_USER, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return mDB.update(DataColumn.UserTable.TABLE_NAME_USER, values, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        int code = uriMatcher.match(uri);
        switch (code) {
            case USER_CODE:
                return "vnd.android.cursor.dir/" + USER_PATH;
            case STUDENT_CODE:
                return "vnd.android.cursor.dir/" + STUDENT_PATH;
        }
        return null;
    }

    private String whereWithId(String id, String selection) {
        StringBuilder sb = new StringBuilder(256);
        sb.append("_id=");
        sb.append(id);
        if (selection != null) {
            sb.append(" AND (");
            sb.append(selection);
            sb.append(')');
        }
        return sb.toString();
    }
}
