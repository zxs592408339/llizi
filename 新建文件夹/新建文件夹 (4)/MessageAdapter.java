package com.ittx.android1601.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ittx.android1601.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义适配器
 */
public class MessageAdapter extends BaseAdapter {
    private static final int ITEM_TYPE_ONE = 0; //三张图片
    private static final int ITEM_TYPE_TWO = 1;
    private List<MessageBean> list = new ArrayList<>(); //数据源集合
    private LayoutInflater layoutInflater;

    public MessageAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<MessageBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 数据源集合元素个数
     *
     * @return
     */
    @Override
    public int getCount() {
        // Logs.e("getCount >>>>>>>>>>>>>>>>>>>>. list.size()  :"+list.size());
        return list.size();
    }

    /**
     * 获取指定位置的集合元素
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        // Logs.e("getItem >>>>>>>>>>>>>>>>>>>>");
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Logs.e("getItemId >>>>>>>>>>>>>>>>>>>>");
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        MessageBean messageBean = (MessageBean) getItem(position);
        String title = messageBean.getTitle();
        if(title.contains("乡村基")){
            return ITEM_TYPE_ONE;
        }else{
            return ITEM_TYPE_TWO;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if(type == ITEM_TYPE_TWO){
            return getOneView(position,convertView,parent);
        }else {
            return getTwoView(position, convertView, parent);
        }
    }

    public View getOneView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            //一级优化  优化View不被重复解析
            convertView = layoutInflater.inflate(R.layout.item_adapter_simple_view, null);
            ImageView iconImg = (ImageView) convertView.findViewById(R.id.adapter_simple_imageview);
            TextView titleTxt = (TextView) convertView.findViewById(R.id.adapter_simple_title_txt);
            TextView contentTxt = (TextView) convertView.findViewById(R.id.adapter_simple_content_txt);

            //二级优化  优化view控件不被重复加载
            viewHolder = new ViewHolder();
            viewHolder.iconImg = iconImg;
            viewHolder.titleTxt = titleTxt;
            viewHolder.contentTxt = contentTxt;
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        //从View对象中获取控件实例
        MessageBean messageBean = (MessageBean) getItem(position);
        viewHolder.iconImg.setImageResource(messageBean.getIcon());
        viewHolder.titleTxt.setText(messageBean.getTitle());
        viewHolder.contentTxt.setText(messageBean.getContent());
        return convertView;
    }

    public View getTwoView(int position, View convertView, ViewGroup parent) {
        ViewTwoHolder viewTwoHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_adpter_two_view, null);
            ImageView leftImageView = (ImageView) convertView.findViewById(R.id.left_imageview);
            ImageView centerImageView = (ImageView) convertView.findViewById(R.id.center_imageview);
            ImageView rightImageView = (ImageView) convertView.findViewById(R.id.right_imageview);

            viewTwoHolder = new ViewTwoHolder();
            viewTwoHolder.leftImageView = leftImageView;
            viewTwoHolder.centerImageView = centerImageView;
            viewTwoHolder.rightImageView = rightImageView;
            convertView.setTag(viewTwoHolder);
        } else {
            viewTwoHolder = (ViewTwoHolder) convertView.getTag();
        }
        MessageBean messageBean = (MessageBean) getItem(position);
        viewTwoHolder.leftImageView.setImageResource(messageBean.getIcon());
        return convertView;
    }

    class ViewHolder {
        ImageView iconImg;
        TextView titleTxt;
        TextView contentTxt;
    }

    class ViewTwoHolder {
        ImageView leftImageView;
        ImageView centerImageView;
        ImageView rightImageView;
    }
}
