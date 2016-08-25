package com.ittx.android1601.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ittx.android1601.R;

public class NewsListFragment extends Fragment {
    private ListView mListView;
    private String[] arrays = new String[]{"新闻1","新闻2","新闻3","新闻4"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_list_layout, container, false);
        mListView = (ListView) v.findViewById(R.id.news_listView);
        ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arrays);
        mListView.setAdapter(adapter);
        return v;
    }

}
