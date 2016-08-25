package com.ittx.android1601.net.aysncktask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Toast;

import com.ittx.android1601.logcat.Logs;

public class DownLoadTask  extends AsyncTask<String,Integer,Boolean>{
    private ProgressDialog mProgressBar;
    private Context mContext;

    public DownLoadTask(Context context){
        mContext = context;
        mProgressBar = new ProgressDialog(context);
        Logs.e("DownLoadTask >>>>>");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.show();
        Logs.e("onPreExecute >>>>>");
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String httpUrl = params[0]; //下载文件地址
        Logs.e("doInBackground >>>>>"+httpUrl);
        int count = 1;
        for(;count<=100;count++){
            SystemClock.sleep(50);
            publishProgress(count);
            Logs.e("doInBackground count>>>>>"+count);
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Logs.e("onProgressUpdate >>>>> values[0]  "+values[0]);
        mProgressBar.setMessage("已经下载 "+values[0]+"%");
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressBar.dismiss();
        Logs.e("onPostExecute aBoolean>>>>>"+aBoolean);
        if(aBoolean){
            Toast.makeText(mContext,"下载成功",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mContext,"下载失败",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

}
