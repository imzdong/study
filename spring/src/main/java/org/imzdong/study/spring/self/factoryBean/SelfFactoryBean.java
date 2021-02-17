package org.imzdong.study.spring.self.factoryBean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class SelfFactoryBean implements FactoryBean<SelfMan> {
    @Override
    public SelfMan getObject() throws Exception {
        SelfMan selfMan = new SelfMan();
        selfMan.setName("SelfFactoryBean");
        selfMan.setAge(18);
        return selfMan;
    }

    @Override
    public Class<?> getObjectType() {
        return SelfMan.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
