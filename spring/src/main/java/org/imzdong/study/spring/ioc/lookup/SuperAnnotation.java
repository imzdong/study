package org.imzdong.study.spring.ioc.lookup;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/20
 */
@Super
public class SuperAnnotation {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SuperAnnotation{" +
                "id=" + id +
                '}';
    }
}
