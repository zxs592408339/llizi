package com.warmtel.okhttp.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
    }

    public void onOkHttpGetBtnClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urls = "http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html";
                String str = "inint";
                try {
                    str = get(urls);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("tag", str);
            }
        }).start();
    }

    public void onOkHttpPostBtnClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = "";
                try {
                    str = postKey("http://192.168.1.139/app/login");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("tag", str);
            }
        }).start();
    }

    public void onOkHttpPostJsonBtnClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonParameter = "{\"username\":\"admin\",\"password\":123456}";
                String str = "";
                try {
                    str = post("http://192.168.1.139/app/jsonurl", jsonParameter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("tag", str);
            }
        }).start();
    }

    /**
     * HTTP GET
     *
     * @param url
     * @return
     * @throws IOException
     */
    private String get(String url) throws IOException {
        int maxCacheSize = 2*1024;

//        client.setCache(new Cache(getCacheDir(),maxCacheSize));
        Interceptor cacheInterceptor = new Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                int maxCacheAge = 2*1024;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")//Pragma:no-cache。在HTTP/1.1协议中，它的含义和Cache-Control:no-cache相同。为了确保缓存生效
                        .header("Cache-Control", String.format("max-age=%d", maxCacheAge))//添加缓存请求头
                        .build();
            }
        };

        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            String jsonTxt = body.string();
            Log.e("tag", "jsonTxt :" + jsonTxt);
            return jsonTxt;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * POST提交Json数据
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */

    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * POST提交键值对
     *
     * @param url
     * @param
     * @return
     * @throws IOException
     */
    private String postKey(String url) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("platform", "android")
                .add("user", "admin")
                .add("psw", "123456")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 文件上传
     * @throws Exception
     */
    public void upFile() throws IOException {
        File file = new File("README.md");

        Request request = new Request.Builder()
                .url("http://192.168.1.139/app")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());
    }
}
