package com.ittx.android1601.datastore.file;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ittx.android1601.R;
import com.ittx.android1601.logcat.Logs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mWriteInternalBtn,mReadInternalBtn,mExternalPulicBtn;
    private TextView mShowMessageTxt;
    private EditText mEdit;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_layout);
        mWriteInternalBtn = (Button) findViewById(R.id.file_write_internal_btn);
        mReadInternalBtn = (Button) findViewById(R.id.file_read_internal_btn);
        mShowMessageTxt = (TextView) findViewById(R.id.file_show_message_txt);
        mExternalPulicBtn = (Button) findViewById(R.id.file_external_putlic_btn);
        mImageView = (ImageView) findViewById(R.id.file_imageview);
        mEdit = (EditText) findViewById(R.id.file_edit);
        mWriteInternalBtn.setOnClickListener(this);
        mReadInternalBtn.setOnClickListener(this);
        mExternalPulicBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.file_write_internal_btn:
//                writeInternalFile();
                writeExternalPicture();
                break;
            case R.id.file_read_internal_btn:
//                readInternalFile();
                readExternalPicture();
                break;
            case R.id.file_external_putlic_btn:
                showExternalFileDir();
                break;
        }
    }

    /**
     * 写内部存储文件
     */
    public void writeInternalFile(){
        String content = mEdit.getText().toString();//"你好 hello";
        String filedir = getFilesDir().toString();
        Logs.e("fileDir :"+filedir);
        File file = new File(getFilesDir(),"test1.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();

            Toast.makeText(this,"写文件成功",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读内部存储文件
     */
    public void readInternalFile(){
        File file = new File(getFilesDir(),"test1.txt");
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length = fis.read(b);
            String msg = new String(b,0,length);
            mShowMessageTxt.setText(msg);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showExternalFileDir(){
        //外部存储公共区域根目录  :/mnt/sdcard
        File fileDir = Environment.getExternalStorageDirectory();
        //外部存储公共区域图片目录 :/mnt/sdcard/Pictures
        File filePictureDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String fileMoviesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).toString();
        String fileMusicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).toString();
        Logs.e("fileDir :"+fileDir);
        Logs.e("filePictureDir :"+filePictureDir);
        Logs.e("fileMoviesDir :"+fileMoviesDir);
        Logs.e("fileMusicDir :"+fileMusicDir);

        //外部存储私有区域根目录 :/mnt/sdcard/Android/data/com.ittx.android1601/files
        File filePrivateDir = getExternalFilesDir(null);
        //外部存储私有区域音乐文件根目录 :/mnt/sdcard/Android/data/com.ittx.android1601/files/Music
        String filePrivateMusicDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC).toString();
        String filePrivatePictureDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();
        Logs.e("filePrivateDir :"+filePrivateDir);
        Logs.e("filePrivateMusicDir :"+filePrivateMusicDir);
        Logs.e("filePrivatePictureDir :"+filePrivatePictureDir);
    }

    /**
     * 检查外部存储是否可用
     * @return
     */
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public void writeExternalPicture(){
        if (!isExternalStorageWritable()) {
            Toast.makeText(this, "外部存储不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "test.png");
        FileOutputStream os;
        try {
            os = new FileOutputStream(file);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.m3);

            bitmap.compress(Bitmap.CompressFormat.PNG, 90, os); //将Bitmap图片对象写入文件输出流,同时规定图片文件格式和质量

            os.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readExternalPicture(){
        if (!isExternalStorageWritable()) {
            Toast.makeText(this, "外部存储不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "test.png");
        Logs.e("file.getPath() "+file.getPath());
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());

        mImageView.setImageBitmap(bitmap);
    }

}
