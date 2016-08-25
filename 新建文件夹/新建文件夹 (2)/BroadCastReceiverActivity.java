package com.ittx.android1601.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ittx.android1601.R;
import com.ittx.android1601.logcat.Logs;
import com.ittx.android1601.music.MusicPlayerActivity;

public class BroadCastReceiverActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String RECEIVER_LOCAL = "com.ittx.android1601.receiver.LOCALBROADCAST";
    private Button mSendReceiverBtn, mSendDTReceiverBtn,mSendLocalReceiverBtn;
    /**
     * 第一步 定义广播接收者
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.ittx.android1601.receiver.MYRECEIVER")) {
                Toast.makeText(BroadCastReceiverActivity.this, "动态广播", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context, MusicPlayerActivity.class));
            }else if(intent.getAction().equals(RECEIVER_LOCAL)){
                Toast.makeText(BroadCastReceiverActivity.this, "接收到本地广播", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast_receiver_layout);
        mSendReceiverBtn = (Button) findViewById(R.id.receiver_send_btn);
        mSendDTReceiverBtn = (Button) findViewById(R.id.receiver_send_dt_btn);
        mSendLocalReceiverBtn = (Button) findViewById(R.id.receiver_send_local_btn);
        mSendReceiverBtn.setOnClickListener(this);
        mSendDTReceiverBtn.setOnClickListener(this);
        mSendLocalReceiverBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        regesterReceiver();
        regesterLoacalReceiver();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.receiver_send_btn:
                sendMyBroadCastReceiver();
                break;
            case R.id.receiver_send_dt_btn:
                sendDTReceiver();
                break;
            case R.id.receiver_send_local_btn:
                sendLocalReceiver();
                break;
        }
    }

    public void sendMyBroadCastReceiver() {
        Intent intent = new Intent();
        intent.setAction("com.ittx.android1601.BroadCastReceiverDEMO");
        sendBroadcast(intent);
    }

    /**
     * 第三步 发送广播
     */
    public void sendDTReceiver() {
        Intent intent = new Intent();
        intent.setAction("com.ittx.android1601.receiver.MYRECEIVER");
        sendBroadcast(intent);
    }

    /**
     * 发送本地广播
     */
    public void sendLocalReceiver(){
        Intent intent = new Intent(RECEIVER_LOCAL);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /**
     * 第二步 注册动态广播
     */
    public void regesterReceiver(){
        Logs.e("注册动态广播");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.ittx.android1601.receiver.MYRECEIVER");
        registerReceiver(receiver, intentFilter);
    }

    /**
     * 注册本地广播
     */
    public void regesterLoacalReceiver(){
        IntentFilter intentFilter = new IntentFilter(RECEIVER_LOCAL);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,intentFilter);
    }

    /**
     * 注册本地广播
     */
    public void unRegesterLocalReceiver(){
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    /**
     * 注销
     */
    public void unRegesterReceiver(){
        Logs.e("注销 动态广播"+receiver);
        if(receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegesterReceiver();
        unRegesterLocalReceiver();
    }


}
