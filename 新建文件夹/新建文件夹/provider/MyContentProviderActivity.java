package com.ittx.android1601.datastore.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ittx.android1601.R;
import com.ittx.android1601.datastore.sqlite.DataColumn;
import com.ittx.android1601.logcat.Logs;
import com.ittx.android1601.music.Music;

import java.util.ArrayList;

public class MyContentProviderActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mQueryBtn,mInsertBtn,mAddStudentBtn,mQueryStudentBtn;
    ArrayList<Music> musicArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_content_provider_layout);
        mQueryBtn = (Button) findViewById(R.id.provider_query_btn);
        mInsertBtn = (Button) findViewById(R.id.provider_insert_btn);
        mAddStudentBtn = (Button) findViewById(R.id.provider_insert_student_btn);
        mQueryStudentBtn = (Button) findViewById(R.id.provider_query_student_btn);
        mQueryBtn.setOnClickListener(this);
        mInsertBtn.setOnClickListener(this);
        mAddStudentBtn.setOnClickListener(this);
        mQueryStudentBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.provider_query_btn:
                queryData();
                break;
            case R.id.provider_insert_btn:
                insertData();
                break;
            case R.id.provider_insert_student_btn:
//                addStudent();
                insertMsg();
                break;
            case R.id.provider_query_student_btn:
//                queryStudent();
//                getMsgs();
                queryMedia();
                break;
        }
    }
    public void addStudent(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataColumn.StudentTable.COLUMN_NAME_NAME,"李四");
        contentValues.put(DataColumn.StudentTable.COLUMN_NAME_AGE,"24");

        Uri uri = Uri.parse(MyContentProvider.CONTENT_URI_STUDENT);
        getContentResolver().insert(uri,contentValues);
    }

    public void insertData(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataColumn.UserTable.COLUMN_NAME_NAME,"丽丽");
        contentValues.put(DataColumn.UserTable.COLUMN_NAME_PASSWORD,"54321");

        Uri uri = Uri.parse(MyContentProvider.CONTENT_URI_USER);
        Uri uri1 = getContentResolver().insert(uri,contentValues);
        Logs.e("insertData :"+uri1.toString());//content://com.ittx.android1601.datastore.provider.MyContentProvider/user/3
    }

    /**
     * 查询数据
     */
    public void queryData(){
        Cursor c = getContentResolver().query(Uri.parse(MyContentProvider.CONTENT_URI_USER),null,null,null,null);
        Logs.e("id    name   password");
        while (c.moveToNext()) {
            int idComumnIndex = c.getColumnIndex(DataColumn.UserTable._ID); //id字段序号
            int nameColumnIndex = c.getColumnIndex(DataColumn.UserTable.COLUMN_NAME_NAME);//name字段序号
            int passwordColumnIndex = c.getColumnIndex(DataColumn.UserTable.COLUMN_NAME_PASSWORD);//password字段序号

            int id = c.getInt(idComumnIndex);
            String name = c.getString(nameColumnIndex);
            String password = c.getString(passwordColumnIndex);

            Logs.e(id + "     " + name + "    " + password);
        }
        c.close();
    }
    public void queryStudent(){
        Cursor c = getContentResolver().query(Uri.parse(MyContentProvider.CONTENT_URI_STUDENT),null,null,null,null);
        Logs.e("id    姓名   年龄");
        Logs.e("c.getCount() "+c.getCount());

        if(c.getCount() <= 0){
            return;
        }

        while(c.moveToNext()) {
            int idComumnIndex = c.getColumnIndex(DataColumn.StudentTable.COLUMN_NAME_ID); //id字段序号
            int nameColumnIndex = c.getColumnIndex(DataColumn.StudentTable.COLUMN_NAME_NAME);//name字段序号
            int ageColumnIndex = c.getColumnIndex(DataColumn.StudentTable.COLUMN_NAME_AGE);//password字段序号

            int id = c.getInt(idComumnIndex);
            String name = c.getString(nameColumnIndex);
            String age = c.getString(ageColumnIndex);

            Logs.e(id + "     " + name + "    " + age);
        }
        c.close();
    }
    /**
     * 短信读取
     */

    private void getMsgs(){
         Uri uri = Uri.parse("content://sms/");
         ContentResolver resolver = getContentResolver();
         //获取的是哪些列的信息
         Cursor cursor = resolver.query(uri, new String[]{"address","date","type","body"}, null, null, null);
         while(cursor.moveToNext())
         {
             String address = cursor.getString(0);
             String date = cursor.getString(1);
             String type = cursor.getString(2);
             String body = cursor.getString(3);
             System.out.println("地址:" + address);
             System.out.println("时间:" + date);
             System.out.println("类型:" + type);
             System.out.println("内容:" + body);
             System.out.println("======================");
             }
         cursor.close();
    }

    /**
     * 插入短信
     */
    private void insertMsg() {
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://sms/");

        ContentValues conValues = new ContentValues();
        conValues.put("address", "123456789");
        conValues.put("type", 1);
        conValues.put("date", System.currentTimeMillis());
        conValues.put("body", "no zuo no die why you try!");

        resolver.insert(uri, conValues);

        Logs.e("短信插入完毕~");
    }

    public void queryMedia(){
        Cursor cursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        /**
         * MediaStore.Audio.Media.EXTERNAL_CONTENT_URI 对应字段
         * 歌曲ID：MediaStore.Audio.Media._ID
         * 歌曲的名称：MediaStore.Audio.Media.TITLE
         * 歌曲的专辑名：MediaStore.Audio.Media.ALBUM
         * 歌曲的歌手名：MediaStore.Audio.Media.ARTIST
         * 歌曲文件的路径：MediaStore.Audio.Media.DATA
         * 歌曲的总播放时长：MediaStore.Audio.Media.DURATION
         * 歌曲文件的大小：MediaStore.Audio.Media.SIZE
         *
         */

        Logs.e("音乐名  "+"艺术家"+"  "+"icon");
        while (cursor.moveToNext()) {
            // 如果不是音乐
            String isMusic = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.IS_MUSIC));
            if (isMusic != null && isMusic.equals("")) continue;

            String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));

            if (isRepeat(title, artist)) continue;

            Music music = new Music();
            music.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
            music.setTitle(title);
            music.setArtist(artist);
            music.setUri(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
            music.setLength(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
            music.setImage(getAlbumImage(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))));

            Logs.e(music.getTitle()+"  "+music.getArtist()+"  "+music.getImage());

            musicArrayList.add(music);
        }
    }

    /**
     * 根据音乐名称和艺术家来判断是否重复包含了
     *
     * @param title
     * @param artist
     * @return
     */
    private boolean isRepeat(String title, String artist) {
        for (Music music : musicArrayList) {
            if (title.equals(music.getTitle()) && artist.equals(music.getArtist())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 根据歌曲id获取图片
     *
     * @param albumId
     * @return
     */
    private String getAlbumImage(int albumId) {
        String result = "";
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(
                    Uri.parse("content://media/external/audio/albums/"
                            + albumId), new String[]{"album_art"}, null,
                    null, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); ) {
                result = cursor.getString(0);
                break;
            }
        } catch (Exception e) {
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }

        return null == result ? null : result;
    }
}
