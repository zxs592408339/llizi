package com.ittx.android1601.net;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.ittx.android1601.R;
import com.ittx.android1601.ui.GridViewActivity;

public class WebViewActivity extends AppCompatActivity {
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_layout);
        mWebView = (WebView) findViewById(R.id.webview);

        //第一步 设置javascript 可用
        mWebView.getSettings().setJavaScriptEnabled(true);

        //第三步 添加javaScript交互接口到webview
        mWebView.addJavascriptInterface(new MyJavaScriptInterface() {
            @Override
            public void showToast() {
//                Toast.makeText(WebViewActivity.this,"网页调用原生android应用对话框",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(WebViewActivity.this, GridViewActivity.class));

            }
        },"MyJavaScriptInterface");

        mWebView.loadUrl("http://192.168.5.5:8080/webapp/login.html");
    }

    //第二步 定义JAVAScript交互接口
    interface MyJavaScriptInterface{
        void showToast();
    }
}
