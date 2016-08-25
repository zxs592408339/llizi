package com.warmtel.customview.basic_two;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.warmtel.customview.R;

public class TitleView extends RelativeLayout {
    private String title;
    private String titleRight;
    private int titleLeftIconRes;
    public TitleView(Context context) {
        super(context);
        inintView(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inintView(context, attrs);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inintView(context, attrs);
    }

    public void inintView(Context context, AttributeSet attrs) {
        TypedArray t = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TitleView, 0, 0);
        try {
             title = t.getString(R.styleable.TitleView_titleText);
             titleRight = t.getString(R.styleable.TitleView_titleRightText);
             titleLeftIconRes = t.getResourceId(R.styleable.TitleView_titleLeftIcon, R.drawable.title_back_selector);
        }finally {
            t.recycle();
        }

        View v = LayoutInflater.from(context).inflate(R.layout.title_view_layout, this, true);

        ImageView leftImg = (ImageView) v.findViewById(R.id.title_left_img);
        TextView titleTxt = (TextView) v.findViewById(R.id.title_center_txt);
        TextView rightTxt = (TextView) v.findViewById(R.id.title_right_txt);

        leftImg.setImageResource(titleLeftIconRes);
        titleTxt.setText(title);
        rightTxt.setText(titleRight);
    }

}
