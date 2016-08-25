package com.ittx.android1601;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ittx.android1601.ui.adapter.MyArrayAdpaterActivity;
import com.ittx.android1601.ui.adapter.MySimpleAdapterActivity;
import com.ittx.android1601.lauchermode.FirstActivity;
import com.ittx.android1601.life.LifeActivity;
import com.ittx.android1601.parameter.FirstParameterActivity;
import com.ittx.android1601.parameter.IntentActivity;
import com.ittx.android1601.parameter.OneActivity;

import java.util.ArrayList;
import java.util.List;

public class LaucherMainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    public ListView mListView;
    public List<MainIntentBean> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laucher_main_layout);
        mListView = (ListView) findViewById(R.id.laucher_main_listview);

        inintData();

        MainIntentAdapter adapter = new MainIntentAdapter(this);
        adapter.setList(list);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }

    /**
     * 初始化数据源
     */
    public void inintData(){
        addItem("第一部UI学习", MainActivity.class);
        addItem("生命周期",LifeActivity.class);
        addItem("Activity启动模式",FirstActivity.class);
        addItem("Intent意图学习",IntentActivity.class);
        addItem("Activity传递参数",OneActivity.class);
        addItem("Activity回传值",FirstParameterActivity.class);
        addItem("ListView适配器ArrayAdapter",MyArrayAdpaterActivity.class);
        addItem("ListView适配器SimpleAdapter",MySimpleAdapterActivity.class);
    }

    public <T> void addItem(String title,Class<T> t){
        MainIntentBean mainIntentBean = new MainIntentBean(title,t);
        list.add(mainIntentBean);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MainIntentAdapter adapter = (MainIntentAdapter) parent.getAdapter();
        MainIntentBean mainIntentBean = (MainIntentBean) adapter.getItem(position);
        Intent intent = new Intent(this,mainIntentBean.toActivityClass);
        startActivity(intent);
    }

    public class MainIntentAdapter extends BaseAdapter{
        private List<MainIntentBean> list = new ArrayList<>();
        private LayoutInflater layoutInflater;
        public MainIntentAdapter(Context context){
            layoutInflater = LayoutInflater.from(context);
        }
        public void setList(List<MainIntentBean> list){
            this.list = list;
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
            if(convertView == null){
                convertView = layoutInflater.inflate(android.R.layout.simple_list_item_1,null);
                TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(textView);
            }
            TextView textView = (TextView) convertView.getTag();

            MainIntentBean item = (MainIntentBean) getItem(position);
            textView.setText(item.title);
            return convertView;
        }
    }


    public class MainIntentBean<T>{
        private String title;
        private Class<T> toActivityClass;

        public MainIntentBean(String title, Class<T> toActivityClass) {
            this.title = title;
            this.toActivityClass = toActivityClass;
        }
    }
}
