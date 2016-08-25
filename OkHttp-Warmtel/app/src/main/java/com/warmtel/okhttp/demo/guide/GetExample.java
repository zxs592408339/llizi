package com.warmtel.okhttp.demo.guide;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GetExample {
    public OkHttpClient client = new OkHttpClient();

    public String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static void main(String[] args) throws IOException {
        GetExample example = new GetExample();

        String response = example.run("http://www.warmtel.com:8080/maininit");
        System.out.println(response);
    }
}
