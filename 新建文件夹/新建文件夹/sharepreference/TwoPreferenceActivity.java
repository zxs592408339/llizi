package com.ittx.android1601.datastore.sharepreference;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ittx.android1601.R;

public class TwoPreferenceActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mShowTxt;
    private Button mGetBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_preference_layout);
        mShowTxt = (TextView) findViewById(R.id.preferenc_two_message_txt);
        mGetBtn = (Button) findViewById(R.id.preferenc_two_get_btn);
        mGetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.preferenc_two_get_btn:
                //从SharedPreferences获取数据
                String message = SharedPreferenceHelper.getInstance(this).getMessage();
                mShowTxt.setText(message);
                break;
        }
    }
}
