package org.imzdong.study.msb.design.single;

public enum  SingleEnum {
    INSTANCE,
    ;

    public void single(){
        System.out.println("I'm enumSingle");
    }

}
