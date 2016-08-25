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

    private ImageView leftImg;
    private TextView titleTxt;
    private TextView rightTxt;

    public void setOnTitleClickListener(OnTitleClickListener onTitleClickListener) {
        this.onTitleClickListener = onTitleClickListener;
    }

    private OnTitleClickListener onTitleClickListener;

    interface OnTitleClickListener {
        void onTitleClick(View v);
    }

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
            titleLeftIconRes = t.getResourceId(R.styleable.TitleView_titleLeftIcon, 0);
        } finally {
            t.recycle();
        }

        LayoutInflater.from(context).inflate(R.layout.title_view_layout, this, true);

        setTitle();
        setTitleRight();
        setTitleLeftIconRes();
    }

    public void setTitleRight() {
        if (rightTxt != null) {
            rightTxt = (TextView) findViewById(R.id.title_right_txt);
            rightTxt.setText(titleRight);
            rightTxt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTitleClickListener != null)
                        onTitleClickListener.onTitleClick(v);
                }
            });
        }
    }

    public void setTitleLeftIconRes() {
        if (titleLeftIconRes != 0) {
            leftImg = (ImageView) findViewById(R.id.title_left_img);
            leftImg.setImageResource(titleLeftIconRes);
            leftImg.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTitleClickListener != null)
                        onTitleClickListener.onTitleClick(v);
                }
            });
        }
    }

    public void setTitle() {
        titleTxt = (TextView) findViewById(R.id.title_center_txt);
        titleTxt.setText(title);
    }

}
