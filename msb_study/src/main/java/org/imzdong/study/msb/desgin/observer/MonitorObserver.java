package org.imzdong.study.msb.desgin.observer;

/**
 * 真正观察者
 */
public class MonitorObserver implements Observer{

    @Override
    public void onWatchEvent(Event event) {
        System.out.println("MonitorObserver:"+event);
    }
}
