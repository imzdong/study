package org.imzdong.study.spring.self.supplier;

public class SupplierUser {

    private String name;

    public SupplierUser() {
    }

    public SupplierUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SupplierUser{" +
                "name='" + name + '\'' +
                '}';
    }
}
