package com.ittx.android1601.net;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ittx.android1601.R;
import com.ittx.android1601.logcat.Logs;
import com.ittx.android1601.net.aysncktask.DownLoadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpActivity extends AppCompatActivity implements View.OnClickListener {
    private String pictureOnedir = "http://img2.imgtn.bdimg.com/it/u=94536769,318259447&fm=23&gp=0.jpg";
    private String pictureTwodir = "http://img03.sogoucdn.com/app/a/100520093/728b3e97663bb9a3-8b6df27c7c8892db-067739029e30aa22042b3d9a2f5b72cd.jpg";
    private Button mShowBtn, mAsysnckTaskShowBtn, mDownLoadBtn;
    private ImageView mImageView;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_layout);
        mShowBtn = (Button) findViewById(R.id.http_show_btn);
        mAsysnckTaskShowBtn = (Button) findViewById(R.id.http_aysncktask_btn);
        mDownLoadBtn = (Button) findViewById(R.id.http_aysncktask_download_btn);
        mImageView = (ImageView) findViewById(R.id.http_imageview);

        mShowBtn.setOnClickListener(this);
        mAsysnckTaskShowBtn.setOnClickListener(this);
        mDownLoadBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.http_show_btn:
                getPictureByThread();
                break;
            case R.id.http_aysncktask_btn:
                getPictureByAsysnckTask();
                break;
            case R.id.http_aysncktask_download_btn:
                downLoadFile();
                break;
        }
    }

    public void downLoadFile() {
        DownLoadTask downLoadTask = new DownLoadTask(this);
        downLoadTask.execute("http://xxx.xxx.xx/file.mp3");

    }

    public void getPictureByThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = doDownLoadPictrue(pictureOnedir);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }


    /**
     * 通过异步任务获取网络图片
     */
    public void getPictureByAsysnckTask() {

        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                String httpUrl = params[0];
                Bitmap bitmap = doDownLoadPictrue(httpUrl);
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                mImageView.setImageBitmap(bitmap);
            }
        }.execute(pictureTwodir);
    }

    /**
     * 从网络获取图片
     *
     * @param httpUrl
     * @return
     */
    public Bitmap doDownLoadPictrue(String httpUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 获取本地文件
     */
    public void getPictureByFile() {
        String pictureFiledir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/test.png";
        Logs.e("pictureFiledir  :" + pictureFiledir);
        File pictureFile = new File(pictureFiledir);
        InputStream fis = null;
        try {
            fis = new FileInputStream(pictureFile);

            Bitmap bitmap = BitmapFactory.decodeStream(fis);

            mImageView.setImageBitmap(bitmap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        mImageView.setImageResource(R.drawable.m1);
    }

    String pictureHttpdir = "http://img03.sogoucdn.com/app/a/100520093/728b3e97663bb9a3-8b6df27c7c8892db-067739029e30aa22042b3d9a2f5b72cd.jpg";

    public void onDownLoad() {
        //创建下载任务,downloadUrl就是下载链接
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pictureHttpdir));
        //指定下载路径和下载文件名
        request.setDestinationInExternalPublicDir("/download/", "mm.jpg");
        //获取下载管理器
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载任务加入下载队列，否则不会进行下载
        downloadManager.enqueue(request);
    }

}
