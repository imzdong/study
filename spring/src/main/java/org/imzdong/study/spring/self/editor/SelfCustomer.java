package org.imzdong.study.spring.self.editor;

public class SelfCustomer {

    private String name;
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SelfCustomer{" +
                "name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
