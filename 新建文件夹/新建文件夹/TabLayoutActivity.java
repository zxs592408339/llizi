package com.ittx.android1601.ui.adapter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ittx.android1601.R;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1_layout_layout);

        mTabLayout = (TabLayout) findViewById(R.id.my_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.my_tab_viewpager);

        List<View> pagerLists = new ArrayList<>();
        setDataLists(pagerLists);

        MyPagerViewAdapter adpater = new MyPagerViewAdapter(pagerLists);

        mViewPager.setAdapter(adpater);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void setDataLists(List<View> pagerLists){
        //第二步数据源
        LayoutInflater inflater = LayoutInflater.from(this);
        View v1 = inflater.inflate(R.layout.pager_item1_view, null);
        View v2 = inflater.inflate(R.layout.pager_item2_view, null);
        View v3 = inflater.inflate(R.layout.pager_item3_view, null);

        pagerLists.add(v1);
        pagerLists.add(v2);
        pagerLists.add(v3);
    }

    public class MyPagerViewAdapter extends PagerAdapter {
        private String[] tabTitle = new String[]{"美女1", "美女2", "美女3"};
        private List<View> pagerLists = new ArrayList<>();

        public MyPagerViewAdapter(List<View> list) {
            pagerLists = list;
        }

        @Override
        public int getCount() {
            return pagerLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = pagerLists.get(position);
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = pagerLists.get(position);
            container.removeView(v);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }
    }
}
