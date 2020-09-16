package org.imzdong.study.msb.day_02;

/**
 * @description:
 * @author: Winter
 * @time: 2020年9月16日, 0016 11:07
 */
public class Single {

    private SingleB singleB;
    private String a;

    public Single(SingleB singleB, String a) {
        this.singleB = singleB;
        this.a = a;
    }

    public Single() {
    }

    public SingleB getSingleB() {
        return singleB;
    }

    public void setSingleB(SingleB singleB) {
        this.singleB = singleB;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "Single{" +
                "singleB=" + singleB +
                ", a='" + a + '\'' +
                '}';
    }
}
