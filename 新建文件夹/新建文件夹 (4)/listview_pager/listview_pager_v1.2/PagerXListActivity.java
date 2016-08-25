package com.ittx.android1601.ui.listview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ittx.android1601.R;
import com.ittx.android1601.logcat.Logs;
import com.ittx.android1601.utils.ConnectionUtils;
import com.warmtel.xlistview.XListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PagerXListActivity extends AppCompatActivity implements XListView.IXListViewListener {
    private static final String baseUrl = "http://192.168.5.11:8080/webapp1/page";
    //    private static final String baseUrl = "http://192.168.5.11:8080/WebAPP/pager";
    private static final int PAGE_SIZE = 30; //每页数据个数
    private int mCurrentPageNo = 1; //当前页号
    private int mTotalPageCount; //总页数
    private XListView mListView;
    private MyPagerAdapter mPagerAdapter;
    private ConnectionUtils mConnectionUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_list_layout);
        mListView = (XListView) findViewById(R.id.page_listview);

        mConnectionUtils = new ConnectionUtils(this);
        mPagerAdapter = new MyPagerAdapter(this);
        mListView.setAdapter(mPagerAdapter);

        mListView.setXListViewListener(this);
        mListView.setPullLoadEnable(true); //上拉加载更多开关
        mListView.setPullRefreshEnable(true);   //下拉刷新开关

        getDataLists(mCurrentPageNo);
    }

    public void getDataLists(int current) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageNo", String.valueOf(current));
        paramsMap.put("pageSize", String.valueOf(PAGE_SIZE));

        mConnectionUtils.asycnConnect(baseUrl, paramsMap, ConnectionUtils.Method.GET, new ConnectionUtils.HttpConnectionInterface() {
            @Override
            public void execute(String content) {
                if (content == null) {
                    Toast.makeText(PagerXListActivity.this, "请求出错!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Logs.e("content :"+content);
                mListView.stopLoadMore();
                mListView.stopRefresh();
                mListView.setRefreshTime("刚刚");

                Gson gson = new Gson();
                PageContent pageContent = gson.fromJson(content, PageContent.class);
                mTotalPageCount = pageContent.getPagecount();
                List<MsgBean> list = pageContent.getContent();

                mPagerAdapter.setDataList(list);
            }
        });
    }

    @Override
    public void onRefresh() {
        Logs.e("onRefresh");
        mCurrentPageNo = 1;
        getDataLists(mCurrentPageNo);
    }

    @Override
    public void onLoadMore() {
        if (++mCurrentPageNo > mTotalPageCount) {
            mCurrentPageNo = mTotalPageCount;
            mListView.stopLoadMore();
            Toast.makeText(this, "已加载到最后一页", Toast.LENGTH_SHORT).show();
            return;
        }
        getDataLists(mCurrentPageNo);

    }

    class MyPagerAdapter extends BaseAdapter {
        private List<MsgBean> list = new ArrayList<>();
        private LayoutInflater layoutInflater;

        public MyPagerAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        public void setDataList(List<MsgBean> list) {
            if (mCurrentPageNo == 1) {
                this.list = list;
            } else {
                this.list.addAll(list);
            }
            notifyDataSetChanged();
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
            if (convertView == null) {
                convertView = layoutInflater.inflate(android.R.layout.simple_list_item_1, null);
            }
            TextView itemTxt = (TextView) convertView;
            MsgBean item = (MsgBean) getItem(position);
            String content = item.getMsg();
            itemTxt.setText(content);

            return convertView;
        }
    }
}
