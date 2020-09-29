package org.imzdong.study.msb.desgin.single;

public enum  SingleEnum {
    INSTANCE,
    ;

    public void single(){
        System.out.println("I'm enumSingle");
    }

}
