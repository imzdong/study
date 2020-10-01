package org.imzdong.study.msb.desgin.observer;

import java.util.LinkedList;
import java.util.List;

public class Monitored {

    private List<Observer> list = new LinkedList<>();
    public void addObserver(Observer observer){
        list.add(observer);
    }
    public void eat(){
        System.out.println("Monitored eat....");
        MonitorEvent monitorEvent = new MonitorEvent();
        monitorEvent.setMonitored(this);
        list.forEach(f->f.onWatchEvent(monitorEvent));
    }
}
