package com.ittx.android1601.datastore.sharepreference;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ittx.android1601.R;

public class SarePreferenceActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mMessageEdit;
    private Button mSaveBtn,mGetBtn;
    private TextView mShowMessageTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sare_preference_layout);
        mMessageEdit = (EditText) findViewById(R.id.preferenc_edit);
        mSaveBtn = (Button) findViewById(R.id.preferenc_save_btn);
        mGetBtn = (Button) findViewById(R.id.preferenc_get_btn);
        mShowMessageTxt = (TextView) findViewById(R.id.preferenc_message_txt);

        mSaveBtn.setOnClickListener(this);
        mGetBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.preferenc_save_btn:
                String msg = mMessageEdit.getText().toString();
                SharedPreferenceHelper.getInstance(this).saveMessage(msg);
                break;
            case R.id.preferenc_get_btn:
                Intent intent = new Intent(this,TwoPreferenceActivity.class);
                startActivity(intent);
                break;
        }
    }
}
