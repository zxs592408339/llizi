package com.ittx.news.slidemenu;

import android.os.Bundle;

import com.ittx.news.R;
import com.warmtel.slidingmenu.lib.SlidingMenu;
import com.warmtel.slidingmenu.lib.app.SlidingActivity;

public class NewsMainActivity extends SlidingActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main_layout);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.news_main_layout,NewsTextFragment.newInstance())
                .commit();

        setBehindContentView(R.layout.sliding_menu_layout);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.sliding_menu_layout,MenuFragment.newInstance())
                .commit();

        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setSlidingEnabled(true);
        slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_off_width);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setMode(SlidingMenu.LEFT);

    }
}
