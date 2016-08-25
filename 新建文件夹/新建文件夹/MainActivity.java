package com.ittx.android1601;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public Button mEventBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //将activity_main布局文件加载到activity界面上

        mEventBtn = (Button) findViewById(R.id.event_btn);
        mEventBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,EventActivity.class);
        startActivity(intent);
    }

    public void onClickLinerLayoutListener(View v){
        Intent intent = new Intent(this,LinearLayoutActivity.class);
        startActivity(intent);
    }
}
