package com.ittx.android1601.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ittx.android1601.R;

/**
 * Fragment嵌入Activity两种方式
 * 1.静态
 * 2.动态  FragmentManager  FragmentTrancaction
 */
public class MainFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment_layout);
    }
}
