package com.ittx.android1601.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ittx.android1601.R;
import com.ittx.android1601.utils.Constances;

public class ImageViewActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private MyImagePageradapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_layou);
        //第一步实例化控件
        mViewPager = (ViewPager) findViewById(R.id.fragment_image_viewpager);
        mAdapter = new MyImagePageradapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        //第二步数据源
        String[] mImageUrls = Constances.imageThumbUrls;
        mAdapter.setImageUrls(mImageUrls);
    }

    //第三步适配器
    class MyImagePageradapter extends FragmentStatePagerAdapter {
        private String[] mImageUrls = new String[]{};

        private void setImageUrls(String[] urls) {
            this.mImageUrls = urls;
            notifyDataSetChanged();
        }

        public MyImagePageradapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageViewFragment.newInstance(mImageUrls[position],position);
        }

        @Override
        public int getCount() {
            return mImageUrls.length;
        }
    }
}
