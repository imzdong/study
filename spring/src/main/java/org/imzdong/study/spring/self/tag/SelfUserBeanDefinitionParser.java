package org.imzdong.study.spring.self.tag;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class SelfUserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return SelfUser.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String name = element.getAttribute("name");
        String password = element.getAttribute("password");
        if(StringUtils.hasLength(name)){
            builder.addPropertyValue("name" , name);
        }
        if(StringUtils.hasLength(password)){
            builder.addPropertyValue("password" , password);
        }
    }
}
