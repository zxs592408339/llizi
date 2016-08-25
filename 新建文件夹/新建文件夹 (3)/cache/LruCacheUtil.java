package com.ittx.android1601.net.cache;


import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.ittx.android1601.logcat.Logs;

public class LruCacheUtil {
    private LruCache<String, Bitmap> mMemoryCache = null;
    private static LruCacheUtil sLruCahceUtil = null;
    public static LruCacheUtil getInsantance(){
        if(sLruCahceUtil == null){
            sLruCahceUtil = new LruCacheUtil();
        }
        return sLruCahceUtil;
    }

    public LruCacheUtil() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory(); // 获取应用程序最大可用内存
        int cacheSize = maxMemory / 8;// 设置图片缓存大小为程序最大可用内存的1/8

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
        Logs.e("LruCacheUtil mMemoryCache "+mMemoryCache);
    }

    /**
     * 将一张图片存储到LruCache中。
     *
     * @param key    LruCache的键，这里传入图片的URL地址。
     * @param bitmap LruCache的值，这里传入从网络上下载的Bitmap对象。
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     *
     * @param key LruCache的键，这里传入图片的URL地址。
     * @return 对应传入键的Bitmap对象，或者null。
     */
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }
}
