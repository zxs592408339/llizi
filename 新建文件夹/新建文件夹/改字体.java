package com.ittx.android1601;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class TextViewActivity extends AppCompatActivity {
    public TextView mSpanningTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_layout);
        mSpanningTxt = (TextView) findViewById(R.id.textview_spanning_txt);

        String content = mSpanningTxt.getText().toString();

        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)),
                4, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(35,true),
                4, 7,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),
                4, 7,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new StrikethroughSpan(),
                11, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.RED),
                11, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpanningTxt.setText(spannableString);
    }
}
