package com.ittx.android1601.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ittx.android1601.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyBaseAdapter extends BaseAdapter{
    private List<String> list = new ArrayList<>();
    List<HashMap<String,Object>> lists = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    public MyBaseAdapter(Context c,List<String> l){
        this.list = l;
        this.context = c;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = (String)getItem(position);
        View v = layoutInflater.inflate(R.layout.item_adapter_array_view,null);
        TextView textView = (TextView) v.findViewById(R.id.item_text);
        textView.setText(item);
        return textView;
    }
}
