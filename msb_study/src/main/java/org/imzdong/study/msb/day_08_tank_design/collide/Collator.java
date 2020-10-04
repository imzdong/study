package org.imzdong.study.msb.day_08_tank_design.collide;

import org.imzdong.study.msb.day_08_tank_design.GameObject;

/**
 * @see java.util.Comparator
 */
public interface Collator {

    boolean collision(GameObject o1, GameObject o2);
}
