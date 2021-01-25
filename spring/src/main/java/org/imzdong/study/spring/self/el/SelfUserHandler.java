package org.imzdong.study.spring.self.el;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class SelfUserHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("self-user", new SelfUserBeanDefinitionParser());
    }
}
