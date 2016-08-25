package com.ittx.android1601.ui.adapter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ittx.android1601.R;
import com.ittx.android1601.logcat.Logs;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TextView mPage1Txt,mPage2Txt,mPage3Txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_layout);
        //第一步 控件
        mViewPager = (ViewPager) findViewById(R.id.adapter_viewpager);
        mPage1Txt = (TextView) findViewById(R.id.pager1_txt);
        mPage2Txt = (TextView) findViewById(R.id.pager2_txt);
        mPage3Txt = (TextView) findViewById(R.id.pager3_txt);

        //第二步数据源
        LayoutInflater inflater = LayoutInflater.from(this);
        View v1 = inflater.inflate(R.layout.pager_item1_view,null);
        View v2 = inflater.inflate(R.layout.pager_item2_view,null);
        View v3 = inflater.inflate(R.layout.pager_item3_view,null);

        List<View> pagerLists = new ArrayList<>();
        pagerLists.add(v1);
        pagerLists.add(v2);
        pagerLists.add(v3);

        //第三步适配器
        MyPagerViewAdapter adapter = new MyPagerViewAdapter(pagerLists);

        mViewPager.setAdapter(adapter);

        mPage1Txt.setTextColor(Color.RED);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Logs.e("onPageSelected position "+position);
                switch (position){
                    case 0:
                        mPage1Txt.setTextColor(Color.RED);
                        mPage2Txt.setTextColor(Color.WHITE);
                        mPage3Txt.setTextColor(Color.WHITE);
                        break;
                    case 1:
                        mPage1Txt.setTextColor(Color.WHITE);
                        mPage2Txt.setTextColor(Color.RED);
                        mPage3Txt.setTextColor(Color.WHITE);
                        break;
                    case 2:
                        mPage1Txt.setTextColor(Color.WHITE);
                        mPage2Txt.setTextColor(Color.WHITE);
                        mPage3Txt.setTextColor(Color.RED);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    public class MyPagerViewAdapter extends PagerAdapter{
        private List<View> pagerLists = new ArrayList<>();
        public MyPagerViewAdapter(List<View> list){
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
    }


}
