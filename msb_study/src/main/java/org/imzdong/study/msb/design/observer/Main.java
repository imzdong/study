package org.imzdong.study.msb.design.observer;

/**
 * 监听者模式，需要有事件，解藕监听者与被监听者
 */
public class Main {

    public static void main(String[] args) {
        Monitored monitored = new Monitored();
        monitored.addObserver(new MonitorObserver());
        monitored.eat();
    }
}
