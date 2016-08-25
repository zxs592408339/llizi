package com.warmtel.okhttp.demo.guide;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostExample {
    public static final MediaType JSON  = MediaType.parse("application/json; charset=utf-8");

    public OkHttpClient client = new OkHttpClient();

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    String bowlingJson() {
        return  "user=admin&psw=123456";
    }

    public static void main(String[] args) throws IOException {
        PostExample example = new PostExample();
        String json = example.bowlingJson();
        String response = example.post("http://192.168.5.8/app/print", "");
        System.out.println(response);
    }
}
