package com.warmtel.rxjava.observer;


import java.util.Observable;
import java.util.Observer;

public class MainObserverTest {
    public void testObserver() {
        Observer zhangs = new ZhangObserver();
        Observer lishi = new LiObserver();

        Observable wangObserverable = new WangObserverable();

        wangObserverable.addObserver(zhangs);  //观察者订阅被观察者 <==> 粉丝关注明星
        wangObserverable.addObserver(lishi);

        wangObserverable.notifyObservers("我是宝宝，明天复婚!");
    }

    public static void main(String[] args) {
        new MainObserverTest().testObserver();
    }

}
