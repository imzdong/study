package org.imzdong.study.msb.day_08_design_tank.tank;

import java.io.Serializable;

/**
 * @author Winter
 * @see java.util.Comparator
 */
public interface FireStrategy<T> extends Serializable {

    void fire(T t);
}
