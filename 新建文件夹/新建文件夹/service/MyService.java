package com.ittx.android1601.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;

import com.ittx.android1601.logcat.Logs;

/**
 * startService()
 * bindService()
 * <p/>
 * stopService()
 * stopSelf()
 * <p/>
 * 实现组件与服务Service之间通讯服务端步骤:
 * 第一步：定义ICount通讯接口,抽象方法
 * 第二步：定义ICountService类继承Binder实现ICount通讯接口
 * 第三步: 实例化ICountService类,将实例化对象作为返回值在onBind方法中返回。
 */
public class MyService extends Service {
    private int mCount = 0;
    private boolean mFlag = true;

    //第一步：定义通讯接口,抽象方法
    interface ICount {
        int getCount(); //获取计数器值
        void stopCount();//停止计数
    }

    //第二步：定义ICountService类继承Binder实现ICount通讯接口
    public class ICountService extends Binder implements ICount {

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public void stopCount() {
            mFlag = false;
        }
    }

    //第三步: 实例化ICountService类,将实例化对象作为返回值在onBind方法中返回。
    public ICountService iCountService = new ICountService();

    public MyService() {
        Logs.e("MyService 构造方法 >> ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logs.e("MyService onCreate >> ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mFlag) {
                    mCount++;
                    Logs.e("count >>>>  :" + mCount);
                    SystemClock.sleep(1000);
                }
            }
        }).start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logs.e("MyService onStartCommand >> ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logs.e("MyService onBind >> ");
        return iCountService;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Logs.e("MyService onUnbind >> ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Logs.e("MyService onDestroy >> ");
        super.onDestroy();
        iCountService.stopCount();
    }
}
