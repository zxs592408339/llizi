package com.ittx.android1601.pattern;

import android.os.SystemClock;

import com.ittx.android1601.logcat.Logs;

/**
 * 员工类
 */
public class Employee {
    // TODO: 2016/7/27 第一步  1.定义接口
    //通讯接口
    public interface CallBackInterface {
        void callMe(String message);
    }
    // TODO: 2016/7/27   2.申明接口
    private CallBackInterface mCallBackInterface;

    // TODO: 2016/7/27   3. 实现注册接口方法
    public void setOnCallBackInterface(CallBackInterface callBackInterface){
        this.mCallBackInterface = callBackInterface;
    }

    private String name;

    public Employee(String name){
        this.name = name;
    }

    public void doUICode(){
        //UI界面开发
        for(int i = 5 ; i > 0 ; i--){
            SystemClock.sleep(1000);
            Logs.e("UI开发完成倒计时"+i);
        }
        // TODO: 2016/7/27  第三步 员工调用接口
        mCallBackInterface.callMe("UI开发完成！");
    }
}
