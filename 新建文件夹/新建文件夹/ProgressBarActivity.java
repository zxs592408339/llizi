package com.ittx.android1601.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ittx.android1601.R;
import com.ittx.android1601.logcat.Logs;

public class ProgressBarActivity extends AppCompatActivity {
    public ProgressBar mProgressBar;
    public Button mConfirmBtn;
    public TextView mShowTextView;

    //作用:1.发送消息sendMessage(Message obj)   2.处理消息 handlerMessage(Message obj)
    public Handler mHandler;

    /**
     * Android主线程也叫UI线程
     *

     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar_layout);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBarone);
        mConfirmBtn = (Button) findViewById(R.id.progressbar_confirm_btn);
        mShowTextView = (TextView) findViewById(R.id.progressbar_show_txtview);

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgressThread();
            }
        });

        //作用:1.发送消息sendMessage(Message obj)   2.处理消息 handlerMessage(Message obj)
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //代码主线程执行
                String str = (String) msg.obj;
                mShowTextView.setText(str);
            }
        };
    }

    public void startProgressThread(){
        mProgressBar.setMax(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 100; i++) {
                    mProgressBar.setProgress(i);
                    SystemClock.sleep(50);
                }
                Logs.e("加载完成！");

                Message msg = Message.obtain();
                msg.obj = "加载完成!";
                mHandler.sendMessage(msg);
            }
        }).start();
    }


    /**
     * 创建线程方式
     * 1.继承 线程类  Thread
     *       MyThread myThread = new MyThread();
     *       myThread.start();
     * 2.实现 Runnable接口
     *      Thread it = new Thread(new MyRunnable());
     *      it.start();
     */
    public class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            for (int i = 1; i <= 100; i++) {
                mProgressBar.setProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class MyRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 100; i++) {
                mProgressBar.setProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
