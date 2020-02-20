package org.imzdong.study.spring.ioc.lookup;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/20
 */
public class SuperLookUp extends LookUp {

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SuperLookUp{" +
                "address='" + address + '\'' +
                '}';
    }
}
