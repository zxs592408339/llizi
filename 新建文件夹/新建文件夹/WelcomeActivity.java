package com.ittx.android1601.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ittx.android1601.LaucherMainActivity;
import com.ittx.android1601.R;

public class WelcomeActivity extends AppCompatActivity {
    private TextView mTimeTxt;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_layout);
        mTimeTxt = (TextView) findViewById(R.id.welcome_txt);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                int type = msg.arg1;
                switch (type){
                    case 0:
                        Intent intent = new Intent(WelcomeActivity.this, LaucherMainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        String count = (String) msg.obj;
                        mTimeTxt.setText(count);
                        break;
                }

            }
        };

        Message message = Message.obtain();
        message.arg1 = 0;
        mHandler.sendMessageDelayed(message,4000);

        new Thread(new Runnable() {
            int count;
            @Override
            public void run() {
                for(count = 4; count >= 1; count--){
                    Message message = Message.obtain();
                    message.arg1 = 1;
                    message.obj = count+"";
                    mHandler.sendMessage(message);

                    SystemClock.sleep(1000);
                }
            }
        }).start();
    }
}
