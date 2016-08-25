package com.ittx.android1601.fragment.subfragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ittx.android1601.R;

public class SubFragamentMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_fragament_main_layout);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.sub_list_fragment_layout,MyListFragment.newInstance())
                .commit();
    }
}
