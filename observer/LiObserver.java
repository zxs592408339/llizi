package com.warmtel.rxjava.observer;

import java.util.Observable;
import java.util.Observer;

public class LiObserver implements Observer {
    @Override
    public void update(Observable observable, Object data) {
        System.out.println("李四收到信息 : "+data);
    }
}
