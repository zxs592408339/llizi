package com.warmtel.rxjava.observer;


import java.util.Observable;
import java.util.Observer;

public class ZhangObserver implements Observer{
    @Override
    public void update(Observable observable, Object data) {
        System.out.println("张三收到信息 : "+data);
    }
}
