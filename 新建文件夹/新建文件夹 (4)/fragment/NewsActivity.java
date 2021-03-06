package com.ittx.android1601.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ittx.android1601.R;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_layout);

        NewsListFragment newsListFragment = new NewsListFragment();
        NewsContentFragment newsContentFragment = new NewsContentFragment();

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.add(R.id.news_list_framgent_layout, newsListFragment);
//        transaction.add(R.id.news_content_framgent_layout, newsContentFragment);
//        transaction.commit();

        getSupportFragmentManager().
                beginTransaction().
                add(R.id.news_list_framgent_layout, newsListFragment).
                add(R.id.news_content_framgent_layout, newsContentFragment).
                commit();

    }
}
