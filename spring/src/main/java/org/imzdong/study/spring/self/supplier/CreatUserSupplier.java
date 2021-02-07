package org.imzdong.study.spring.self.supplier;

import java.util.function.Supplier;

public class CreatUserSupplier implements Supplier<SupplierUser> {
    @Override
    public SupplierUser get() {
        return new SupplierUser("咚锵咚");
    }
}
