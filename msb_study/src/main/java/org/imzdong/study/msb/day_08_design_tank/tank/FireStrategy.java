package org.imzdong.study.msb.day_08_design_tank.tank;

/**
 * @author Winter
 * @see java.util.Comparator
 */
public interface FireStrategy<T> {

    void fire(T t);
}
