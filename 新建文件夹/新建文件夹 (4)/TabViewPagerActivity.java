package com.ittx.android1601.fragment.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.ittx.android1601.R;
import com.ittx.android1601.fragment.parameter.NewsContentFragment;
import com.ittx.android1601.fragment.parameter.NewsListFragment;
import com.ittx.android1601.ui.listview.PagerXListFragment;

import java.util.ArrayList;
import java.util.List;


public class TabViewPagerActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener {
    private Fragment newsListFragment, newsContentFragment, pagerListFragment;
    private LinearLayout mOneTabLayout;
    private LinearLayout mTwoTabLayout;
    private LinearLayout mThreeTabLayout;
    private ViewPager mViewPager;
    private MyViewPagerAdapter mMyViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_tab_layout);

        mViewPager = (ViewPager) findViewById(R.id.fragments_content);
        mOneTabLayout = (LinearLayout) findViewById(R.id.tab_indicator_one);
        mTwoTabLayout = (LinearLayout) findViewById(R.id.tab_indicator_two);
        mThreeTabLayout = (LinearLayout) findViewById(R.id.tab_indicator_three);
        mOneTabLayout.setOnClickListener(this);
        mTwoTabLayout.setOnClickListener(this);
        mThreeTabLayout.setOnClickListener(this);
        mOneTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        List<Fragment> mDataLists = getDataLists();
        mMyViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMyViewPagerAdapter);
        mMyViewPagerAdapter.setDataLists(mDataLists);
        mViewPager.addOnPageChangeListener(this);
    }

    /**
     * 设置ViewPager数据源
     */
    public List<Fragment> getDataLists() {
        List<Fragment> mDataLists = new ArrayList<>();
        newsListFragment = new NewsListFragment();
        newsContentFragment = new NewsContentFragment();
        pagerListFragment = new PagerXListFragment();
        mDataLists.add(newsListFragment);
        mDataLists.add(newsContentFragment);
        mDataLists.add(pagerListFragment);
        return mDataLists;
    }

    @Override
    public void onClick(View v) {
        clearBackgroundColor();
        switch (v.getId()) {
            case R.id.tab_indicator_one:
                mViewPager.setCurrentItem(0);
                mOneTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
            case R.id.tab_indicator_two:
                mViewPager.setCurrentItem(1);
                mTwoTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
            case R.id.tab_indicator_three:
                mViewPager.setCurrentItem(2);
                mThreeTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
        }
    }

    public void clearBackgroundColor() {
        mOneTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTwoTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mThreeTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        clearBackgroundColor();
        switch (position){
            case 0:
                mOneTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
            case 1:
                mTwoTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
            case 2:
                mThreeTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> dataLists = new ArrayList<>();

        private void setDataLists(List<Fragment> list) {
            this.dataLists = list;
            notifyDataSetChanged();
        }

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return dataLists.get(position);
        }

        @Override
        public int getCount() {
            return dataLists.size();
        }
    }

}
