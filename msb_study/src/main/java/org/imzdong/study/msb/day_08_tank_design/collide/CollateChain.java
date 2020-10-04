package org.imzdong.study.msb.day_08_tank_design.collide;

import org.imzdong.study.msb.day_08_tank_design.GameObject;

import java.util.LinkedList;
import java.util.List;

/**
 * 责任链模式
 * 自己也实现Collator 链穿链
 */
public class CollateChain implements Collator{

    private List<Collator> collators = new LinkedList<>();

    public void add(Collator collator){
        collators.add(collator);
    }

    @Override
    public boolean collision(GameObject o1, GameObject o2) {
        for (int i = 0; i < collators.size(); i++) {
            if(!collators.get(i).collision(o1, o2)){
                return false;
            }
        }
        return true;
    }
}
