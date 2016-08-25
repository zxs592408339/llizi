package com.warmtel.okhttp.demo;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by viktor on 2016/3/2.
 */
public class OkHttpManager {
    private static String DEFAULT_CACHE_DIR_PIC = "pic";
    private static long maxCacheSize = 5 * 1024 * 1024; //5M
    private static int maxCacheAge = 10;//3600 * 1;  //1h
    private static long timeOut = 10000;  //10s
    private static long readTimeOut = 15000; //15s
    private static OkHttpClient mOkHttpClient;

    public enum CacheType {
        ONLY_NETWORK,ONLY_CACHED,CACHED_ELSE_NETWORK,NETWORK_ELSE_CACHED
    }

    public static void init(Context context) {
        File cacheDir = null;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            // cacheDir 缓存路径 /mnt/sdcard/Android/data/<pkg name>/cache/<name>
            // fileDir 缓存路径 /mnt/sdcard/Android/data/<pkg name>/files/<name>
            cacheDir = new File(context.getExternalCacheDir(), DEFAULT_CACHE_DIR_PIC);
        } else {
            // cacheDir 缓存路径 /data/data/<pkg name>/cache/<name>
            cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR_PIC);
        }

        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                // TODO: 2016/3/2 max-age=xxx (xxx is numeric) 缓存的内容将在 xxx 秒后失效,
                // 这个选项只在HTTP 1.1可用, 并如果和Last-Modified一起使用时, 优先级较高
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", String.format("max-age=%d", maxCacheAge))
                        .build();
            }
        };

        mOkHttpClient = new OkHttpClient().newBuilder()
                .cache(new Cache(cacheDir, maxCacheSize))
                .addNetworkInterceptor(cacheInterceptor)
                .connectTimeout(timeOut, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                .build();
    }

    public static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient != null) {
            return mOkHttpClient;
        } else {
            throw new IllegalStateException("OkHttpClient not initialized");
        }
    }

    public static Response executeRequest(Request requste) throws IOException {
        return getOkHttpClient().newCall(requste).execute();
    }

    public static void executeRequest(Request requste, final Callback callBack) {
        getOkHttpClient().newCall(requste).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onResponse(call, response);
            }
        });
    }

    public static void executeRequest(CacheType cacheType, final Request.Builder builder, final Callback callBack) {
        switch (cacheType) {
            case ONLY_NETWORK:
                getOkHttpClient().newCall(builder.cacheControl(CacheControl.FORCE_NETWORK).build()).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callBack.onFailure(call, e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        callBack.onResponse(call, response);
                    }
                });
                break;
            case ONLY_CACHED:
                getOkHttpClient().newCall(builder.cacheControl(CacheControl.FORCE_CACHE).build()).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callBack.onFailure(call, e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        callBack.onResponse(call, response);
                    }
                });
                break;
            case CACHED_ELSE_NETWORK:
                OkHttpManager.getOkHttpClient().newCall(builder.cacheControl(CacheControl.FORCE_CACHE).build()).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callBack.onFailure(call, e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.code() == 504) {
                            OkHttpManager.getOkHttpClient().newCall(builder.cacheControl(CacheControl.FORCE_NETWORK).build()).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    callBack.onFailure(call, e);
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    callBack.onResponse(call, response);
                                }
                            });
                        } else {
                            callBack.onResponse(call, response);
                        }
                    }
                });
                break;
            case NETWORK_ELSE_CACHED:
                OkHttpManager.getOkHttpClient().newCall(builder.cacheControl(CacheControl.FORCE_NETWORK).build()).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callBack.onFailure(call, e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.code() == 200) {
                            callBack.onResponse(call, response);
                        } else {
                            OkHttpManager.getOkHttpClient().newCall(builder.cacheControl(CacheControl.FORCE_CACHE).build()).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    callBack.onFailure(call, e);
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    callBack.onResponse(call, response);
                                }
                            });
                        }
                    }
                });
                break;
        }
    }


}
