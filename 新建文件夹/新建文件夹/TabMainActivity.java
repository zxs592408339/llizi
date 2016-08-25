package com.ittx.android1601.ui.tab;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.ittx.android1601.R;
import com.ittx.android1601.ui.GridViewActivity;
import com.ittx.android1601.ui.ProgressBarActivity;
import com.ittx.android1601.ui.adapter.MySimpleAdapterActivity;

public class TabMainActivity extends TabActivity implements View.OnClickListener{
    private static final int TAB_LIST = 0;
    private static final int TAB_GRID = 1;
    private static final int TAB_PROGRESS_BAR = 2;
    private TabHost mTabHost;
    private LayoutInflater inflater;
    private LinearLayout mOneTabLayout;
    private LinearLayout mTwoTabLayout;
    private LinearLayout mThreeTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main_layout);

        mOneTabLayout = (LinearLayout) findViewById(R.id.tab_indicator_one);
        mTwoTabLayout = (LinearLayout) findViewById(R.id.tab_indicator_two);
        mThreeTabLayout = (LinearLayout) findViewById(R.id.tab_indicator_three);
        mTabHost = getTabHost();

        inflater = LayoutInflater.from(this);

        TabHost.TabSpec tabSpec1 = mTabHost.newTabSpec("tab1");
        tabSpec1.setIndicator(createIndicator(TAB_LIST));
        tabSpec1.setContent(new Intent(this, MySimpleAdapterActivity.class));

        TabHost.TabSpec tabSpec2 = mTabHost.newTabSpec("tab2");
        tabSpec2.setIndicator(createIndicator(TAB_GRID));
        tabSpec2.setContent(new Intent(this, GridViewActivity.class));

        TabHost.TabSpec tabSpec3 = mTabHost.newTabSpec("tab3");
        tabSpec3.setIndicator(createIndicator(TAB_PROGRESS_BAR));
        tabSpec3.setContent(new Intent(this, ProgressBarActivity.class));

        mTabHost.addTab(tabSpec1);
        mTabHost.addTab(tabSpec2);
        mTabHost.addTab(tabSpec3);

        mOneTabLayout.setOnClickListener(this);
        mTwoTabLayout.setOnClickListener(this);
        mThreeTabLayout.setOnClickListener(this);


        clearBackground();
        mOneTabLayout.getChildAt(0).setBackgroundResource(R.drawable.ic_menu_poi_on);
    }

    /**
     * 创建自定义指示器
     * @param pageNo
     * @return
     */
    public String createIndicator(int pageNo) {
        return ""+pageNo;
//        View tabview = inflater.inflate(R.layout.tab_item_layout, null);
//        ImageView iconImg = (ImageView) tabview.findViewById(R.id.tab_indicator_img);
//        TextView titleTxt = (TextView) tabview.findViewById(R.id.tab_indicator_txt);
//        switch (pageNo) {
//            case TAB_LIST:
//                iconImg.setImageResource(R.drawable.tab_indector_selector_one);
//                titleTxt.setText("首页");
//                break;
//            case TAB_GRID:
//                iconImg.setImageResource(R.drawable.tab_indector_selector_two);
//                titleTxt.setText("团购");
//                break;
//            case TAB_PROGRESS_BAR:
//                iconImg.setImageResource(R.drawable.tab_indector_selector_three);
//                titleTxt.setText("设置");
//                break;
//        }
//
//        return tabview;
    }


    @Override
    public void onClick(View v) {
        clearBackground();
        switch(v.getId()){
            case R.id.tab_indicator_one:
                mTabHost.setCurrentTab(TAB_LIST);
                mOneTabLayout.getChildAt(0).setBackgroundResource(R.drawable.ic_menu_poi_on);
                break;
            case R.id.tab_indicator_two:
                mTabHost.setCurrentTab(TAB_GRID);
                mTwoTabLayout.getChildAt(0).setBackgroundResource(R.drawable.ic_menu_user_on);
                break;
            case R.id.tab_indicator_three:
                mTabHost.setCurrentTab(TAB_PROGRESS_BAR);
                mThreeTabLayout.getChildAt(0).setBackgroundResource(R.drawable.ic_menu_more_on);
                break;

        }
    }
    public void clearBackground(){
        mOneTabLayout.getChildAt(0).setBackgroundResource(R.drawable.ic_menu_poi_off);
        mTwoTabLayout.getChildAt(0).setBackgroundResource(R.drawable.ic_menu_user_off);
        mThreeTabLayout.getChildAt(0).setBackgroundResource(R.drawable.ic_menu_more_off);
    }

}
