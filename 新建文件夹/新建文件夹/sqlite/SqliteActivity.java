package com.ittx.android1601.datastore.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ittx.android1601.R;
import com.ittx.android1601.logcat.Logs;

public class SqliteActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mAddDataBtn, mQueryBtn, mAndroidAddBtn, mAndroidUpdateBtn, mAndroidDeleteBtn, mAndroidQueryBtn;
    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_layout);
        mAddDataBtn = (Button) findViewById(R.id.sqlite_add_data_btn);
        mQueryBtn = (Button) findViewById(R.id.sqlite_query_btn);
        mAndroidAddBtn = (Button) findViewById(R.id.sqlite_android_add_btn);
        mAndroidUpdateBtn = (Button) findViewById(R.id.sqlite_android_update_btn);
        mAndroidDeleteBtn = (Button) findViewById(R.id.sqlite_android_delete_btn);
        mAndroidQueryBtn = (Button) findViewById(R.id.sqlite_android_query_btn);

        mAddDataBtn.setOnClickListener(this);
        mQueryBtn.setOnClickListener(this);
        mAndroidUpdateBtn.setOnClickListener(this);
        mAndroidDeleteBtn.setOnClickListener(this);
        mAndroidQueryBtn.setOnClickListener(this);
        mAndroidAddBtn.setOnClickListener(this);//注册mAndroidAddBtn按钮监听事件

        mDataBaseHelper = new DataBaseHelper(this);
        sqLiteDatabase = mDataBaseHelper.getReadableDatabase();//创建或者打开数据库，创建数据库时回调DataBaseHelper类中oncreate()方法
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sqlite_add_data_btn:
                String sqladd = "insert into user values(2,\"李思\",\"abc\")";
                sqLiteDatabase.execSQL(sqladd);

                Toast.makeText(this, "添加数据成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sqlite_query_btn:
                String querySQL = "select * from user";
                Cursor cursor = sqLiteDatabase.rawQuery(querySQL, null);
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String password = cursor.getString(cursor.getColumnIndex("password"));
                    Logs.e("name :" + name + " password :" + password);
                }
                break;
            case R.id.sqlite_android_add_btn:
                ContentValues contentValues = new ContentValues();
                contentValues.put(DataColumn.UserTable.COLUMN_NAME_ID, 3);
                contentValues.put(DataColumn.UserTable.COLUMN_NAME_NAME, "王二");
                contentValues.put(DataColumn.UserTable.COLUMN_NAME_PASSWORD, "321");
                sqLiteDatabase.insert(DataColumn.UserTable.TABLE_NAME_USER, null, contentValues);
                break;
            case R.id.sqlite_android_update_btn:
                // update user set name="李四" where name="李思"
                ContentValues contentValues1 = new ContentValues();
                contentValues1.put(DataColumn.UserTable.COLUMN_NAME_NAME, "李四");

                String whereCauser = DataColumn.UserTable.COLUMN_NAME_NAME + " = ?";
                String[] whereArgs = new String[]{"李思"};
                sqLiteDatabase.update(DataColumn.UserTable.TABLE_NAME_USER, contentValues1, whereCauser, whereArgs);

                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sqlite_android_delete_btn:
                String whereClause = DataColumn.UserTable.COLUMN_NAME_NAME + " = ? and " + DataColumn.UserTable.COLUMN_NAME_ID + " = ?";
                String[] whereArg = new String[]{"李四", "2"};
                sqLiteDatabase.delete(DataColumn.UserTable.TABLE_NAME_USER, whereClause, whereArg);

                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sqlite_android_query_btn:
                String[] colume = new String[]{DataColumn.UserTable.COLUMN_NAME_ID, DataColumn.UserTable.COLUMN_NAME_NAME, DataColumn.UserTable.COLUMN_NAME_PASSWORD};
                String selectClause = DataColumn.UserTable.COLUMN_NAME_ID + " = ?";
                String[] selectArgs = new String[]{"1"};

                Cursor c = sqLiteDatabase.query(DataColumn.UserTable.TABLE_NAME_USER, colume, null, null, null, null, null);

                Logs.e("id    name   password");
                while (c.moveToNext()) {
                    int idComumnIndex = c.getColumnIndex(DataColumn.UserTable.COLUMN_NAME_ID); //id字段序号
                    int nameColumnIndex = c.getColumnIndex(DataColumn.UserTable.COLUMN_NAME_NAME);//name字段序号
                    int passwordColumnIndex = c.getColumnIndex(DataColumn.UserTable.COLUMN_NAME_PASSWORD);//password字段序号

                    int id = c.getInt(idComumnIndex);
                    String name = c.getString(nameColumnIndex);
                    String password = c.getString(passwordColumnIndex);

                    Logs.e(id + "     " + name + "    " + password);
                }
                break;
        }
    }
}
