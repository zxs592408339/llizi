package com.ittx.android1601.fragment.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.ittx.android1601.R;
import com.ittx.android1601.fragment.life.LifeFragment;
import com.ittx.android1601.fragment.parameter.NewsContentFragment;
import com.ittx.android1601.fragment.parameter.NewsListFragment;

public class TabFragmentMainActivity extends AppCompatActivity implements View.OnClickListener{
    private NewsListFragment newsListFragment;
    private NewsContentFragment newsContentFragment;
    private LifeFragment lifeFragment;
    private LinearLayout mOneTabLayout;
    private LinearLayout mTwoTabLayout;
    private LinearLayout mThreeTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_tab_main_layout);
        newsListFragment = new NewsListFragment();
        newsContentFragment = new NewsContentFragment();
        lifeFragment = new LifeFragment();

        mOneTabLayout = (LinearLayout) findViewById(R.id.tab_indicator_one);
        mTwoTabLayout = (LinearLayout) findViewById(R.id.tab_indicator_two);
        mThreeTabLayout = (LinearLayout) findViewById(R.id.tab_indicator_three);
        mOneTabLayout.setOnClickListener(this);
        mTwoTabLayout.setOnClickListener(this);
        mThreeTabLayout.setOnClickListener(this);

        mOneTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragments_content,newsListFragment)
                .commit();
    }

    @Override
    public void onClick(View v) {
        clearBackgroundColor();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch(v.getId()){
            case R.id.tab_indicator_one:
                if(newsListFragment == null){
                    newsListFragment = new NewsListFragment();
                }
                fragmentTransaction.replace(R.id.fragments_content, newsListFragment);
                mOneTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
            case R.id.tab_indicator_two:
                if(newsContentFragment == null){
                    newsContentFragment = new NewsContentFragment();
                }
                fragmentTransaction.replace(R.id.fragments_content, newsContentFragment);
                mTwoTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
            case R.id.tab_indicator_three:
                if(lifeFragment == null){
                    lifeFragment = new LifeFragment();
                }
                fragmentTransaction.replace(R.id.fragments_content, lifeFragment);
                mThreeTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
        }
        fragmentTransaction.commit();
    }

    public void clearBackgroundColor(){
        mOneTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTwoTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mThreeTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }
}
