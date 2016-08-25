package com.ittx.android1601.ui.listview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ittx.android1601.R;
import com.ittx.android1601.logcat.Logs;
import com.ittx.android1601.utils.ConnectionUtils;
import com.warmtel.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;


/**
 *   1.头条新闻列表接口
 参数定义:
 int pageNo = 0; //页号 ，表示第几页,第一页从0开始
 int pageSize = 20; //页大小，显示每页多少条数据
 String news_type_id = "T1348647909107";  //新闻类型标识, 此处表示头条新闻
 请地址: "http://c.m.163.com/nc/article/headline/"+ news_type_id +pageNo*pageSize+ "-"  +pageSize+ ".html"
 请求方式:Get
 例如: http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html    //表示请求头条新闻第一页20条数据
 http://c.m.163.com/nc/article/headline/T1348647909107/20-20.html    //表示请求头条新闻第二页20条数据
 http://c.m.163.com/nc/article/headline/T1348647909107/40-20.html    //表示请求头条新闻第三页20条数据
 */
public class PagerXListFragment extends Fragment implements XListView.IXListViewListener {
    private String news_type_id = "T1348647909107";  //新闻类型标识, 此处表示头条新闻
    private int pageSize = 20; //页大小，显示每页多少条数据
    private int mCurrentPageNo = 1; //当前页号
    private int mTotalPageCount; //总页数
    private XListView mListView;
    private MyPagerAdapter mPagerAdapter;
    private LinearLayout empltyView;
    private ConnectionUtils mConnectionUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pager_list_layout,container,false);
        mListView = (XListView) v.findViewById(R.id.page_listview);
        empltyView = (LinearLayout) v.findViewById(R.id.progress_layout);
        mConnectionUtils = new ConnectionUtils(getContext());
        mPagerAdapter = new MyPagerAdapter(getContext());
        mListView.setAdapter(mPagerAdapter);
        mListView.setEmptyView(empltyView); //如果listView适配器数据源为空则显示emptyView否则显示listview

        mListView.setXListViewListener(this);
        mListView.setPullLoadEnable(true); //上拉加载更多开关
        mListView.setPullRefreshEnable(true);   //下拉刷新开关

        getDataLists(mCurrentPageNo);

        return v;
    }

    public void getDataLists(int current) {

        String baseUrl = "http://c.m.163.com/nc/article/headline/"+ news_type_id +"/"+(current-1)*pageSize+ "-" +pageSize+ ".html";

        mConnectionUtils.asycnConnect(baseUrl, ConnectionUtils.Method.GET, new ConnectionUtils.HttpConnectionInterface() {
            @Override
            public void execute(String content) {
                if (content == null) {
                    Toast.makeText(getContext(), "请求出错!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Logs.e("content :"+content);
                mListView.stopLoadMore();
                mListView.stopRefresh();
                mListView.setRefreshTime("刚刚");

                Gson gson = new Gson();
                NewListsBean newListsBean = gson.fromJson(content, NewListsBean.class);
                List<NewBean> newBeanLists =  newListsBean.getT1348647909107();
                mTotalPageCount = 5; //默认页数

                mPagerAdapter.setDataList(newBeanLists);
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
            Toast.makeText(getContext(), "已加载到最后一页", Toast.LENGTH_SHORT).show();
            return;
        }
        getDataLists(mCurrentPageNo);

    }

    class MyPagerAdapter extends BaseAdapter {
        private List<NewBean> list = new ArrayList<>();
        private LayoutInflater layoutInflater;

        public MyPagerAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        public void setDataList(List<NewBean> list) {
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
            NewBean item = (NewBean) getItem(position);
            String content = item.getTitle();
            itemTxt.setText(content);

            return convertView;
        }
    }
}
