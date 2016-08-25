package com.ittx.android1601.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ittx.android1601.R;
import com.ittx.android1601.logcat.Logs;
import com.ittx.android1601.utils.ConnectionUtils;
import com.ittx.android1601.xml.Book;
import com.ittx.android1601.xml.BookParser;
import com.ittx.android1601.xml.PullBookParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HttpTextActivity extends AppCompatActivity implements View.OnClickListener {
    private String httpUrl = "http://192.168.5.11:8080/json/around";
    private String httpLoginUrl = "http://192.168.5.11:8080/webapp/login";
    private TextView mContentTxt;
    private ProgressBar mProgressBar;
    private WebView mWebView;
    private Button mGetContentBtn;
    private ConnectionUtils mHttpConnectionUtils;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_text_layout);
        mContentTxt = (TextView) findViewById(R.id.net_content_txt);
        mGetContentBtn = (Button) findViewById(R.id.net_messag_btn);
        mWebView = (WebView) findViewById(R.id.net_webview);
        mProgressBar = (ProgressBar) findViewById(R.id.net_http_progressBar);
        mGetContentBtn.setOnClickListener(this);

        mHttpConnectionUtils = new ConnectionUtils(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.net_messag_btn:
                Logs.e("isCheckConnection >> :"+isCheckConnection() + " isWiFiActive :"+isWiFiActive(this));
//                if(isWiFiActive(this) || isCheckConnection()){
                    doDownLoadTxt();
//                }else{
//                    Toast.makeText(this,"当前网络环境不可用!",Toast.LENGTH_SHORT).show();
//                }
                break;
        }
    }

    public void doDownLoadTxt(){
//        HashMap<String,String> paramsMap = new HashMap<>();
//        paramsMap.put("user","admin");
//        paramsMap.put("psw","123456");

        mProgressBar.setVisibility(View.VISIBLE);
        mHttpConnectionUtils.asycnConnect(httpUrl, ConnectionUtils.Method.POST, ConnectionUtils.Cache.TRUE, new ConnectionUtils.HttpConnectionInterface() {
            @Override
            public void execute(String content) {
                mProgressBar.setVisibility(View.GONE);
                if(content == null){ //通过content==null 是否有网络
                    Toast.makeText(HttpTextActivity.this, "连接超时,请检查网络环境!", Toast.LENGTH_SHORT).show();
                }else {
                    mContentTxt.setText(content);
                }
            }
        });
    }



    /**
     * 检查网络连接是否可用
     */
    public boolean isCheckConnection(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //网络连接可用
            return true;
        } else {
            //网络连接不可用!
            return false;
        }
    }

    public boolean isWiFiActive(Context inContext) {
        Context context = inContext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void doParseXmlBooks(InputStream is ){
        BookParser parser = new PullBookParser();
        ArrayList<Book> books = null;
        try {
            books = (ArrayList<Book>) parser.parse(is);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Book b : books) {
            Logs.v("name:" + b.getName());
        }
    }

    /**
     * 解析商家json字符串
     * @param jsonStr
     */
    public void doMerchantJsonParase(String jsonStr){
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject jsonInfo = jsonObject.getJSONObject("info");
            JSONArray jsonArray = jsonInfo.getJSONArray("merchantKey");
            int length = jsonArray.length();
            Logs.e("name        location           pictUrl");
                for(int i = 0; i < length; i++){
                JSONObject jsonItem = jsonArray.getJSONObject(i);
                String name = jsonItem.getString("name");
                String location = jsonItem.getString("location");
                String picUrl = jsonItem.getString("picUrl");
                Logs.e(name+"  "+location+"  "+picUrl);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * 解析学生json字符串
     {"students":[{"name":"小明","age":18,"id":112,"sex":"男"},{"name":"小王","age":17,"id":102,"sex":"女"}]}
     * @param jsonStr
     */
    public void doStudentJsonParase(String jsonStr){
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("students");

            int length = jsonArray.length();
            Logs.e("name  age  id  sex");
            for(int i = 0 ; i <length ; i++){
                JSONObject itemJson = jsonArray.getJSONObject(i);
                String name = itemJson.getString("name");
                int age = itemJson.getInt("age");
                int id = itemJson.getInt("id");
                String sex = itemJson.getString("sex");
                Logs.e(name+"  "+age+"  "+id+"  "+sex);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
