package org.imzdong.study.spring.self.editor;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

public class AddressPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            String[] addresses = text.split(",");
            Address address = new Address();
            address.setProvince(addresses[0]);
            address.setCity(addresses[1]);
            address.setDistrict(addresses[2]);
            setValue(address);
        }
    }
}
