package com.ittx.android1601.net;

import android.graphics.Bitmap;

import java.util.HashMap;

public class BitmapCacheUtil {
    public static HashMap<String,Bitmap> sBitmapMap = new HashMap<>();

    /**
     * 保存图片到内存缓存
     * @param url
     * @param bitmap
     */
    public static void putBitmap(String url,Bitmap bitmap){
        HashMap<String,Bitmap> item = new HashMap<>();
        sBitmapMap.put(url,bitmap);
    }

    /**
     * 从内存缓存获取图片
     * @param url
     * @return
     */
    public static Bitmap getBitmap(String url){
        return sBitmapMap.get(url);
    }

    /**
     * 清理缓存
     */
    public static void clear(){
        sBitmapMap.clear();
    }

}
