package com.ittx.android1601.service.demo;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

import com.ittx.android1601.logcat.Logs;

public class DownLoadFileService extends Service {
    public Handler mHandler;

    public DownLoadFileService() {
    }

    class LooperThread extends Thread{
        Looper mLooper;
        @Override
        public void run() {
            Looper.prepare();

            Looper.loop();
        }
        public Looper getLooper() {
            if (!isAlive()) {
                return null;
            }

            // If the thread has been started, wait until the looper has been created.
            synchronized (this) {
                while (isAlive() && mLooper == null) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
            return mLooper;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logs.e("onCreate >>>> ");
        HandlerThread handlerThread = new HandlerThread("downLoadFile");
        handlerThread.start();

        Looper looper = handlerThread.getLooper();

        mHandler = new Handler(looper){
            @Override
            public void handleMessage(Message msg) {
                Intent intent = (Intent) msg.obj;
                String fileName = intent.getStringExtra("FileName");
                String fileDir = intent.getStringExtra("FileDir");
                downFile(fileName,fileDir);

                stopSelf(msg.arg1);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message message = Message.obtain();
        message.arg1 = startId;
        message.obj = intent;

        mHandler.sendMessage(message);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 后台下载文件
     * @param fileName
     * @param fileDir
     */
    public void startDown(final String fileName, final String fileDir, final int startId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                downFile(fileName,fileDir);
                stopSelf(startId);
            }
        }).start();
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
