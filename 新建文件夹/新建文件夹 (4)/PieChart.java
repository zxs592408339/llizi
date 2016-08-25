package com.warmtel.customview.basic_one;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.warmtel.customview.R;


/**
 * 为什么自定义View
 * 自定义view流程
 * 1.定义View属性
 * 2.获取View属性值
 * 3.计算宽高onMesure
 * 1>三种Model
 * EXACTLY:  设置了明确的值或者是MATCH_PARENT
 * AT_MOST:  WARP_CONTENT
 * UNSPECIFIED:  子布局想要多大就多大，很少使用
 * 2>计算文本内容宽高
 * Rect rect = new Rect();
 * paint.getTextBounds("文本内容", 0, textContent.length(), rect);
 * 4.绘制onDraw
 */
public class PieChart extends View {
    private String textContent;
    private int textColor;
    private float textSize;
    private Paint paint;
    private Rect rect;
    private OnCustomeClickListener onClickListener;

    public interface OnCustomeClickListener {
        void onClick(View v);
    }

    public void setCustomeOnClickListener(OnCustomeClickListener l) {
        onClickListener = l;
    }

    public PieChart(Context context) {
        super(context);
        inint(context, null);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        inint(context, attrs);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inint(context, attrs);
    }

    public void inint(Context context, AttributeSet attrs) {
        TypedArray t = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PieChart, 0, 0);
        try {
            textContent = t.getString(R.styleable.PieChart_content);
            textColor = t.getColor(R.styleable.PieChart_textcolor, Color.RED);
//          textSize = t.getDimension(R.styleable.PieChart_textsize, 16);
            textSize = t.getDimensionPixelSize(R.styleable.PieChart_textsize, (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        } finally {
            t.recycle();
        }

        paint = new Paint();
        paint.setTextSize(textSize);

        rect = new Rect();
        paint.getTextBounds(textContent, 0, textContent.length(), rect);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
            }
        });
    }

    public void ininView(Context context, AttributeSet attrs) {
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, 0, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.PieChart_content) {
                textContent = a.getString(attr);

            } else if (attr == R.styleable.PieChart_textcolor) {// 默认颜色设置为黑色
                textColor = a.getColor(attr, Color.BLACK);

            } else if (attr == R.styleable.PieChart_textsize) {// 默认设置为16sp，TypeValue也可以把sp转化为px
                textSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));

            }
//            switch (attr)
//            {
//                case R.styleable.PieChart_content:
//                    textContent = a.getString(attr);
//                    break;
//                case R.styleable.PieChart_textcolor:
//                    // 默认颜色设置为黑色
//                    textColor = a.getColor(attr, Color.BLACK);
//                    break;
//                case R.styleable.PieChart_textsize:
//                    // 默认设置为16sp，TypeValue也可以把sp转化为px
//                    textSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
//                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
//                    break;
//
//            }
        }
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;

        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:  //设置了明确的值或者是MATCH_PARENT
                width = specSize;
                break;
            case MeasureSpec.AT_MOST:  //WARP_CONTENT
                width = rect.width() + getPaddingLeft() + getPaddingRight();
                break;
            case MeasureSpec.UNSPECIFIED:  //子布局想要多大就多大，很少使用
                break;
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:  //设置了明确的值或者是MATCH_PARENT
                height = specSize;
                break;
            case MeasureSpec.AT_MOST:  //WARP_CONTENT
                height = rect.height() + getPaddingBottom() + getPaddingTop();
                break;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.DKGRAY);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

        paint.setColor(textColor);
        canvas.drawText(textContent, getWidth() / 2 - rect.width() / 2, getHeight() / 2 + rect.height() / 2, paint);
    }
}
