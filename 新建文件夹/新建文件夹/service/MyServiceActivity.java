package com.ittx.android1601.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ittx.android1601.R;
import com.ittx.android1601.logcat.Logs;

/**
 * 实现组件与服务Service之间通讯组件端步骤:
 *  第一步:申明通讯接口
 *  第二步:实例化ServiceConnection类重写里面方法onServiceConnected,onServiceDisconnected
 *         在onServiceConnected方法中实例化通讯接口
 *
 *
 */
public class MyServiceActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mStartServiceBtn, mStopServiceBtn, mBindServiceBtn, mGetCountBtn,mUnBindBtn,mStartBindBtn;
    private TextView mShowCountTxt;
    private MyService.ICount iCountService; //第一步:申明通讯接口

    //第二步:实例化ServiceConnection类
    public ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logs.e("onserviceConnected >>>>>>>>>>");
            iCountService = (MyService.ICountService) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logs.e("onServiceDisconnected >>>>>>>>>>");
            iCountService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service_layout);
        mStartServiceBtn = (Button) findViewById(R.id.service_start_btn);
        mStopServiceBtn = (Button) findViewById(R.id.service_stop_btn);
        mBindServiceBtn = (Button) findViewById(R.id.service_bind_btn);
        mGetCountBtn = (Button) findViewById(R.id.service_get_count_btn);
        mUnBindBtn = (Button) findViewById(R.id.service_unbind_btn);
        mStartBindBtn = (Button) findViewById(R.id.service_start_bind_btn);
        mShowCountTxt = (TextView) findViewById(R.id.service_show_count_txt);

        mStartServiceBtn.setOnClickListener(this);
        mStopServiceBtn.setOnClickListener(this);
        mBindServiceBtn.setOnClickListener(this);
        mGetCountBtn.setOnClickListener(this);
        mUnBindBtn.setOnClickListener(this);
        mStartBindBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.service_start_btn:
                startMyService();
                break;
            case R.id.service_stop_btn:
                stopMyService();
                break;
            case R.id.service_bind_btn:
                bindMyService();
                break;
            case R.id.service_get_count_btn:
                getCount();
                break;
            case R.id.service_unbind_btn:
                unBindMyService();
                break;
            case R.id.service_start_bind_btn:
                startAndBindService();
                break;
        }
    }

    /**
     * 启动服务
     */
    public void startMyService() {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    /**
     * 停止服务
     */
    public void stopMyService() {
        stopService(new Intent(this, MyService.class));
    }

    /**
     * 绑定服务
     */
    public void bindMyService() {
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    /**
     * 获取计数器当前值
     */
    public void getCount() {
        int count = iCountService.getCount();
        mShowCountTxt.setText(String.valueOf(count));
    }

    /**
     * 解绑服务
     */
    public void unBindMyService(){
        iCountService.stopCount();
        unbindService(serviceConnection);
    }

    public void startAndBindService(){
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }
}
