package com.warmtel.volleyapp.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.warmtel.volleyapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DispatchDemoActivity extends AppCompatActivity {
    private InnerListView mListView;
    private ScrollView mScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_demo_layout);

        mListView = (InnerListView) findViewById(R.id.dis_listview);
        mScrollView = (ScrollView) findViewById(R.id.dis_scroll);
        mListView.setParentScrollView(mScrollView);

        ArrayList<HashMap<String, Object>> listData = new ArrayList<>();
        setData(listData);
        String[] from = { "icon", "title", "content" };
		int[] to = { R.id.simple_icon_img, R.id.simple_title_txt,R.id.simple_content_txt };
		SimpleAdapter adapter = new SimpleAdapter(this, listData,
				R.layout.adapter_simple_item1_layout, from, to);

        mListView.setAdapter(adapter);
    }

    public void setData(ArrayList<HashMap<String, Object>> listData){
        HashMap<String, Object> item = new HashMap<String, Object>();
        item.put("icon", R.drawable.list1);
        item.put("title", "1【多店通用】乡村基");
        item.put("content", "20元代金券！全场通用，可叠加使用，提供免费WiFi！");
        listData.add(item);

        item = new HashMap<String, Object>();
        item.put("icon", R.drawable.list2);
        item.put("title", "2【多店通用】廖记棒棒鸡");
        item.put("content", "32元代金券！全场通用，可叠加使用，节假日通用！");
        listData.add(item);

        item = new HashMap<String, Object>();
        item.put("icon", R.drawable.list3);
        item.put("title", "3【5店通用】九锅一堂");
        item.put("content", "32元代金券！全场通用，可叠加使用，节假日通用！");
        listData.add(item);

        item = new HashMap<String, Object>();
        item.put("icon", R.drawable.list4);
        item.put("title", "4【幸福大道】囧囧串串");
        item.put("content", "32元代金券！全场通用，可叠加使用，节假日通用！");
        listData.add(item);


        item.put("icon", R.drawable.list1);
        item.put("title", "5【多店通用】乡村基");
        item.put("content", "20元代金券！全场通用，可叠加使用，提供免费WiFi！");
        listData.add(item);

        item = new HashMap<String, Object>();
        item.put("icon", R.drawable.list2);
        item.put("title", "6【多店通用】廖记棒棒鸡");
        item.put("content", "32元代金券！全场通用，可叠加使用，节假日通用！");
        listData.add(item);

        item = new HashMap<String, Object>();
        item.put("icon", R.drawable.list3);
        item.put("title", "7【5店通用】九锅一堂");
        item.put("content", "32元代金券！全场通用，可叠加使用，节假日通用！");
        listData.add(item);

        item = new HashMap<String, Object>();
        item.put("icon", R.drawable.list4);
        item.put("title", "8【幸福大道】囧囧串串");
        item.put("content", "32元代金券！全场通用，可叠加使用，节假日通用！");
        listData.add(item);
    }
}
