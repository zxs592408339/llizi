package com.ittx.android1601.datastore.sharepreference;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {
    public SharedPreferences mSharedPreferences;
    public static SharedPreferenceHelper sHelper;
    public static SharedPreferenceHelper getInstance(Context context){
        if(sHelper == null){
            sHelper = new SharedPreferenceHelper(context);
        }
        return sHelper;
    }

    private SharedPreferenceHelper(Context context){
        //实例化SharedPreferences对象
        mSharedPreferences = context.getSharedPreferences("com.ittx.android1601.PREFERENCES",context.MODE_PRIVATE);
    }

    public void saveMessage(String message){
        mSharedPreferences.edit().putString("MESSAGE",message).commit();
    }

    public String getMessage(){
        return mSharedPreferences.getString("MESSAGE","");
    }

}
