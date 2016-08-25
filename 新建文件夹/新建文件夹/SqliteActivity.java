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

public class SqliteActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mAddDataBtn,mQueryBtn,mAndroidAddBtn,mAndroidUpdateBtn,mAndroidDeleteBtn,mAndroidQueryBtn;
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
        sqLiteDatabase = mDataBaseHelper.getReadableDatabase();//创建或者打开数据库，回调DataBaseHelper类中oncreate()方法
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sqlite_add_data_btn:
                String sqladd = "insert into user values(2,\"李思\",\"abc\")";
                sqLiteDatabase.execSQL(sqladd);

                Toast.makeText(this,"添加数据成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sqlite_query_btn:
                String querySQL = "select * from user";
                Cursor cursor = sqLiteDatabase.rawQuery(querySQL,null);
                while(cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String password = cursor.getString(cursor.getColumnIndex("password"));
                    Logs.e("name :"+name+" password :"+password);
                }
                break;
            case R.id.sqlite_android_add_btn:
                ContentValues contentValues = new ContentValues();
                contentValues.put("id",3);
                contentValues.put("name","王二");
                contentValues.put("password","321");

                sqLiteDatabase.insert("user",null,contentValues);
                break;
            case R.id.sqlite_android_update_btn:

                // update user set name="李四" where name="李思"
                ContentValues contentValues1 = new ContentValues();
                contentValues1.put("name","李四");

                String whereCauser = "name = ?";
                String[] whereArgs = new String[]{"李思"};

                sqLiteDatabase.update("user",contentValues1,whereCauser,whereArgs);

                Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sqlite_android_delete_btn:
                String whereClause = "name = ? and id = ?";
                String[] whereArg = new String[]{"李四","2"};
                sqLiteDatabase.delete("user",whereClause,whereArg);

                Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sqlite_android_query_btn:
                String[] colume = new String[]{"id","name","password"};
                String selectClause = "id = ?";
                String[] selectArgs = new String[]{"1"};

                Cursor c = sqLiteDatabase.query("user",colume,null,null,null,null,null);

                Logs.e("id    name   password");
                while (c.moveToNext()){
                    int idComumnIndex = c.getColumnIndex("id"); //id字段序号
                    int nameColumnIndex = c.getColumnIndex("name");//name字段序号
                    int passwordColumnIndex = c.getColumnIndex("password");//password字段序号

                    int id = c.getInt(idComumnIndex);
                    String name = c.getString(nameColumnIndex);
                    String password = c.getString(passwordColumnIndex);

                    Logs.e(id+"     "+name+"    "+password);
                }
                break;
        }
    }
}
