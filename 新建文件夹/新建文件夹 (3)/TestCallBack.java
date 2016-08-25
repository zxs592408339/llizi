package com.ittx.android1601;

import android.test.AndroidTestCase;

import com.ittx.android1601.logcat.Logs;
import com.ittx.android1601.pattern.Employee;

/**
 * 同步调用
 * 异步调用
 * 回调实现步骤
 *
 *
 */
public class TestCallBack extends AndroidTestCase {
    public void testCallBack() {
        Employee employee = new Employee("员工A");

        employee.setOnCallBackInterface(new Employee.CallBackInterface() {
            @Override
            public void callMe(String message) {
                Logs.e("老板收到  ：" + message);
            }
        });

        employee.doUICode();

    }

}
