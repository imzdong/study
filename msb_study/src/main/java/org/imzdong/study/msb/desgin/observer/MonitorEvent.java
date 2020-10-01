package org.imzdong.study.msb.desgin.observer;

public class MonitorEvent extends Event<Monitored>{

    private Monitored monitored;

    @Override
    public Monitored getSource() {
        return monitored;
    }

    public void setMonitored(Monitored monitored) {
        this.monitored = monitored;
    }

    @Override
    public String toString() {
        return "MonitorEvent{" +
                "monitored=" + monitored +
                '}';
    }
}
