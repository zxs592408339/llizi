package com.ittx.android1601.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * BroadcastReceiver 是对发送出来的广播进行过滤接收并响应的一类组件；
 */
public class MyBroadCastReceiver extends BroadcastReceiver {
    String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    String MY_RECEIVER = "com.ittx.android1601.BroadCastReceiverDEMO";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Toast.makeText(context, "接收到短信", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(MY_RECEIVER)) {
            Toast.makeText(context, "接收到自定义广播", Toast.LENGTH_SHORT).show();
        }
    }
}
