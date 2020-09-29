package org.imzdong.study.msb.desgin.strategy;

/**
 * 策略
 * @author Winter
 * @see java.util.Comparator
 */
public interface Strategy<T> {
    int compare(T o1, T o2);
}
