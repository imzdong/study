package org.imzdong.study.msb.day_08_design_tank.collide;

import org.imzdong.study.msb.day_08_design_tank.GameObject;

/**
 * @see java.util.Comparator
 */
public interface Collator {

    boolean collision(GameObject o1, GameObject o2);
}
