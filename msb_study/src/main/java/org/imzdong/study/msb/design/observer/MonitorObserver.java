package org.imzdong.study.msb.design.observer;

/**
 * 真正观察者
 */
public class MonitorObserver implements Observer{

    @Override
    public void onWatchEvent(Event event) {
        System.out.println("MonitorObserver:"+event);
    }
}
