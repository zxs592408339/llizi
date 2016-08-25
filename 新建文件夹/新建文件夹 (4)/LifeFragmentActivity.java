package com.ittx.android1601.fragment.life;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ittx.android1601.R;
import com.ittx.android1601.fragment.NewsListFragment;

public class LifeFragmentActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mAddFragmentBtn,mRemoveFragmentBtn,mReplaceBtn;
    private LifeFragment mLifeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_fragment_layout);
        mAddFragmentBtn = (Button) findViewById(R.id.life_add_framgent_btn);
        mRemoveFragmentBtn = (Button) findViewById(R.id.life_remove_framgent_btn);
        mReplaceBtn = (Button) findViewById(R.id.life_replace_framgent_btn);
        mAddFragmentBtn.setOnClickListener(this);
        mRemoveFragmentBtn.setOnClickListener(this);
        mReplaceBtn.setOnClickListener(this);

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

}
