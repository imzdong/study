package org.imzdong.study.spring.self.supplier;

import java.util.function.Supplier;

public class CreatUserSupplier {
    public static SupplierUser get() {
        return new SupplierUser("咚锵咚");
    }
}
