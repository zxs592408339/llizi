package com.ittx.android1601.service.demo;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

import com.ittx.android1601.logcat.Logs;

public class DownLoadIntentService extends IntentService {

    public DownLoadIntentService() {
        super("DownLoadIntent");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String fileName = intent.getStringExtra("FileName");
        String fileDir = intent.getStringExtra("FileDir");
        downFile(fileName,fileDir);
    }
    /**
     * 下载文件
     */
    public void downFile(String fileName,String fileDir){
        Logs.e("开始下载"+fileName);
        SystemClock.sleep(5000);
        Logs.e(fileName+"下载完成!");
    }
}
