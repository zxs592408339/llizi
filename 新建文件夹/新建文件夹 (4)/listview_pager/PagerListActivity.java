package com.ittx.android1601.ui.listview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ittx.android1601.R;
import com.ittx.android1601.logcat.Logs;
import com.ittx.android1601.utils.ConnectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PagerListActivity extends AppCompatActivity implements AbsListView.OnScrollListener {
    private static final String baseUrl = "http://192.168.5.11:8080/webapp/page";
    private static final int PAGE_SIZE = 20; //每页数据个数
    private int mCurrentPageNo = 1; //当前页号
    private int mTotalPageCount; //总页数
    private ListView mListView;
    private ProgressBar mProgressBar;
    private MyPagerAdapter mPagerAdapter;
    private ConnectionUtils mConnectionUtils;
    private boolean isScrollToLast = false; //是否滚动到最底部


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_list_layout);
        mListView = (ListView) findViewById(R.id.page_listview);
        mProgressBar = (ProgressBar) findViewById(R.id.page_progressbar);

        mConnectionUtils = new ConnectionUtils(this);
        mPagerAdapter = new MyPagerAdapter(this);
        mListView.setAdapter(mPagerAdapter);

        mListView.setOnScrollListener(this);

        getDataLists(mCurrentPageNo);
    }

    public void getDataLists(int current) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("pageNo", String.valueOf(current));
        paramsMap.put("pageSize", String.valueOf(PAGE_SIZE));

        mProgressBar.setVisibility(View.VISIBLE);
        mConnectionUtils.asycnConnect(baseUrl, paramsMap, ConnectionUtils.Method.GET, new ConnectionUtils.HttpConnectionInterface() {
            @Override
            public void execute(String content) {
                mProgressBar.setVisibility(View.GONE);
                Gson gson = new Gson();
                PageContent pageContent = gson.fromJson(content, PageContent.class);
                mTotalPageCount = pageContent.getPagecount();
                List<MsgBean> list = pageContent.getContent();

                if(mCurrentPageNo == 1) {
                    mPagerAdapter.setDataList(list);
                }else{
                    mPagerAdapter.addDataList(list);
                }
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Logs.e("onScrollStateChanged >>> "+scrollState);
        if(scrollState == SCROLL_STATE_IDLE && isScrollToLast){
            isScrollToLast = false;
            if(++mCurrentPageNo > mTotalPageCount){
                Toast.makeText(this,"已加载到最后一页",Toast.LENGTH_SHORT).show();
                mCurrentPageNo = mTotalPageCount;
            }else {
                getDataLists(mCurrentPageNo);
            }
        }
    }

    /**
     * 滚动时一直回调，直到停止滚动时才停止回调。单击时回调一次。
     * firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
     * visibleItemCount：当前能看见的列表项个数（小半个也算）
     * totalItemCount：列表项共数
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Logs.e("firstVisibleItem :" + firstVisibleItem + " ,visibleItemCount :" + visibleItemCount + " ,totalItemCount :" + totalItemCount);
        if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
            isScrollToLast = true;
        }
    }


    class MyPagerAdapter extends BaseAdapter {
        private List<MsgBean> list = new ArrayList<>();
        private LayoutInflater layoutInflater;

        public MyPagerAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        public void setDataList(List<MsgBean> list) {
            this.list = list;
            notifyDataSetChanged();
        }
        public void addDataList(List<MsgBean> list){
            this.list.addAll(list);
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
