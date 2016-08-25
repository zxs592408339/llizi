package com.ittx.android1601.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ittx.android1601.R;
import com.ittx.android1601.ui.adapter.MessageAdapter;
import com.ittx.android1601.ui.adapter.MessageBean;

import java.util.ArrayList;
import java.util.List;

public class PopupWindowActivity extends AppCompatActivity {
    private TextView mPopupWindownTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window_layout);

        mPopupWindownTxt = (TextView) findViewById(R.id.poupwindow_textview);

        LayoutInflater inflater = LayoutInflater.from(this);
        View popupContentView = inflater.inflate(R.layout.popupwindow_layout,null);
        ListView popupListView = (ListView) popupContentView.findViewById(R.id.popup_listview);
        MessageAdapter adapter = new MessageAdapter(this);
        adapter.setList(getListMessageData());
        popupListView.setAdapter(adapter);

        final PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setContentView(popupContentView);
        popupWindow.setWidth(400);
        popupWindow.setHeight(400);

        mPopupWindownTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopupWindowActivity.this,"弹出框",Toast.LENGTH_SHORT).show();
                if(popupWindow.isShowing()){
                    popupWindow.dismiss();
                }else{
                    popupWindow.showAsDropDown(v);
                }

            }
        });

    }

    public List getListMessageData() {
        List<MessageBean> listData = new ArrayList();

        MessageBean item = new MessageBean();
        item.setIcon(R.drawable.list1);
        item.setTitle("1【多店通用】乡村基");
        item.setContent("20元代金券！全场通用，可叠加使用，提供免费WiFi！");
        listData.add(item);

        item = new MessageBean();
        item.setIcon(R.drawable.list2);
        item.setTitle("2【多店通用】廖记棒棒鸡");
        item.setContent("32元代金券！全场通用，可叠加使用，节假日通用！");
        listData.add(item);

        item = new MessageBean();
        item.setIcon( R.drawable.list3);
        item.setTitle("3【5店通用】九锅一堂");
        item.setContent("32元代金券！全场通用，可叠加使用，节假日通用！");
        listData.add(item);

        item = new MessageBean();
        item.setIcon( R.drawable.list4);
        item.setTitle("4【幸福大道】囧囧串串");
        item.setContent("32元代金券！全场通用，可叠加使用，节假日通用！");
        listData.add(item);


        item.setIcon( R.drawable.list1);
        item.setTitle("5【多店通用】乡村基");
        item.setContent("20元代金券！全场通用，可叠加使用，提供免费WiFi！");
        listData.add(item);

        item = new MessageBean();
        item.setIcon( R.drawable.list2);
        item.setTitle("6【多店通用】廖记棒棒鸡");
        item.setContent("32元代金券！全场通用，可叠加使用，节假日通用！");
        listData.add(item);

        item = new MessageBean();
        item.setIcon( R.drawable.list3);
        item.setTitle("7【5店通用】九锅一堂");
        item.setContent("32元代金券！全场通用，可叠加使用，节假日通用！");
        listData.add(item);

        item = new MessageBean();
        item.setIcon( R.drawable.list4);
        item.setTitle("8【幸福大道】囧囧串串");
        item.setContent("32元代金券！全场通用，可叠加使用，节假日通用！");
        listData.add(item);

        return listData;
    }
}
