package com.ittx.android1601.fragment.life;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ittx.android1601.R;
import com.ittx.android1601.fragment.NewsListFragment;
import com.ittx.android1601.logcat.Logs;

public class LifeFragmentActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mAddFragmentBtn,mRemoveFragmentBtn,mReplaceBtn,mSendArugementBtn;
    private EditText mMessageEdit;
    private LifeFragment mLifeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logs.v("LifeActivity onCreate >>>>>");
        setContentView(R.layout.activity_life_fragment_layout);
        mAddFragmentBtn = (Button) findViewById(R.id.life_add_framgent_btn);
        mRemoveFragmentBtn = (Button) findViewById(R.id.life_remove_framgent_btn);
        mReplaceBtn = (Button) findViewById(R.id.life_replace_framgent_btn);
        mSendArugementBtn = (Button) findViewById(R.id.life_send_augerments_framgent_btn);
        mMessageEdit = (EditText) findViewById(R.id.life_edit);
        mAddFragmentBtn.setOnClickListener(this);
        mRemoveFragmentBtn.setOnClickListener(this);
        mReplaceBtn.setOnClickListener(this);
        mSendArugementBtn.setOnClickListener(this);

        mLifeFragment = new LifeFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.life_add_framgent_btn:
                addFragment();
                break;
            case R.id.life_remove_framgent_btn:
                removeFragment();
                break;
            case R.id.life_replace_framgent_btn:
                replaceFragment();
                break;
            case R.id.life_send_augerments_framgent_btn:
                sendArugmentToLifeFramgent();
                break;
        }
    }

    public void addFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.life_fragmentlayout,mLifeFragment);
        transaction.commit();
    }

    public void removeFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(mLifeFragment);
        transaction.commit();
    }

    public void replaceFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.life_fragmentlayout,new NewsListFragment());
        transaction.commit();
    }

    public void sendArugmentToLifeFramgent(){
        String message = mMessageEdit.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("MESSAGE",message);
        mLifeFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.life_fragmentlayout,mLifeFragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logs.v("LifeActivity onStart >>>>>");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logs.v("LifeActivity onResume >>>>>");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logs.v("LifeActivity onPause >>>>>");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logs.v("LifeActivity onStop >>>>>");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logs.v("LifeActivity onDestroy >>>>>");
    }
}
