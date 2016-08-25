package com.ittx.android1601.net;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ittx.android1601.R;
import com.ittx.android1601.logcat.Logs;
import com.ittx.android1601.ui.GridViewActivity;

public class WebViewActivity extends AppCompatActivity {
    private WebView mWebView;
    private TextView mLoadTxt;
    private ProgressBar mLoadingSeekBar;

    //第二步 定义JAVAScript交互接口
    interface MyJavaScriptInterface {
        void showToast();
        String htmlToNativeToJs();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_layout);
        mWebView = (WebView) findViewById(R.id.webview);
        mLoadTxt = (TextView) findViewById(R.id.load_progress_txt);
        mLoadingSeekBar = (ProgressBar) findViewById(R.id.load_progreebar);

        setJavascriptInterface();
        setWebVieClient();

        loadData();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void setWebVieClient(){
        mWebView.setWebViewClient(new WebViewClient(){
            /**
             * 请求的url嵌入webview显示
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView webview, String url) {
                webview.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mLoadTxt.setText("正在加载中.....");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLoadTxt.setText("加载完成");
            }

        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Logs.e("onProgressChanged :"+newProgress);
                mLoadingSeekBar.setProgress(newProgress);
            }
        });

    }


    /**
     * 设置javaScript与android原生应用交互接口
     */
    public void setJavascriptInterface() {
        //第一步 设置javascript 可用
        mWebView.getSettings().setJavaScriptEnabled(true);

        //第三步 添加javaScript交互接口到webview
        MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface() {
            @Override
            public void showToast() {
                startActivity(new Intent(WebViewActivity.this, GridViewActivity.class));
            }

            @Override
            public String htmlToNativeToJs() {
                return "http://it.warmtel.com";
            }
        };
        /**
         * WebView.addJavascriptInterface(Object,String)方法
         * Object 交互接口对象
         * String 交互接口名称
         */
        mWebView.addJavascriptInterface(myJavaScriptInterface, "MyJavaScriptInterface");
    }

    /**
     * webView加载数据三种方式
     */
    public void loadData() {
//      mWebView.loadUrl("http://192.168.5.5:8080/webapp/login.html");
        mWebView.loadUrl("file:///android_asset/login.html");
//      mWebView.loadDataWithBaseURL(null,"<html><body><h1>你好 loadDataWithBaswUrl</h1></body></html>","text/html","UTF-8",null);
    }


}
