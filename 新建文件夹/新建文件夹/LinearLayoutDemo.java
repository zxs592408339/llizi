package com.ittx.android1601;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LinearLayoutDemo extends AppCompatActivity implements View.OnClickListener{
    public TextView mShowMessageTxt;
    public Button mConfirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_layout);

        /**
         * 1. 获取布局控件实例  findViewById(int resId);
         * 2. 在代码中引用资源  R.layout.resname    R.drawable.picname    R.id.idname    R.string.strname
         *     R.color.colorname
         *
         */
//        mShowMessageTxt = new TextView(this);
        mShowMessageTxt = (TextView) findViewById(R.id.show_message_txt);
        mConfirmBtn = (Button) findViewById(R.id.confirm_btn);
        mConfirmBtn.setOnClickListener(this);  // 注册事件监听
    }

    @Override
    public void onClick(View v) {
        mShowMessageTxt.setText("你好1111111111");
    }
}
