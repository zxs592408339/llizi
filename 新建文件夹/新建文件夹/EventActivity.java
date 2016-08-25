package com.ittx.android1601;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 1. ID资源引用 ids.xml  <item name="resname" type="id"/>
 * 2. 多个按钮对同一事件监听
 * 3. 事件处理方式
 */
public class EventActivity extends AppCompatActivity implements  View.OnClickListener{
    public TextView mShowMessageTxt;
    public Button mOneConfirmBtn, mTwoConfirmBtn,mThreeConfirmBtn;

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
        mShowMessageTxt = (TextView) findViewById(R.id.show_message_txt);
        mOneConfirmBtn = (Button) findViewById(R.id.one_confirm_btn);
        mTwoConfirmBtn = (Button) findViewById(R.id.two_confirm_btn);
        mThreeConfirmBtn = (Button) findViewById(R.id.three_confirm_btn);

        //todo 接口实现 注册事件监听
        mOneConfirmBtn.setOnClickListener(this);

        // todo 事件处理:匿名内部类实现
        mTwoConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowMessageTxt.setText("匿名内部类实现");
            }
        });

        //todo 内部类实现 注册事件监听
        MyOnClickListener myOnClickListener = new MyOnClickListener();
        mThreeConfirmBtn.setOnClickListener(myOnClickListener);
    }

    /**
     * 事件处理1:接口实现
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.one_confirm_btn:
                mShowMessageTxt.setText("接口实现");
                break;
        }
    }

    /**
     * 事件处理2:内部类实现
     */
    class MyOnClickListener implements  View.OnClickListener{

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.three_confirm_btn:
                    mShowMessageTxt.setText("内部类实现");
                    break;
            }
        }
    }

    /**
     * Android事件处理方式
     * @param v
     */
    public void OnclickAndroidListner(View v){
        mShowMessageTxt.setText("Android事件处理实现");
    }

}
