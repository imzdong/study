package org.imzdong.study.spring.ioc.beans;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyEditorSupport;
import java.util.stream.Stream;

/**
 * @description:
 * JavaBeans作为IOC容器
 * {@link java.beans.BeanInfo}
 * @author: Winter
 * @since
 */
public class BeanInfoDemo {

    /**
     * JavaBeans是一种综合需求的基础，简单讲，包含Bean自省（Bean内容描述），Bean时间，Bean内容的修改等元数据
     * 并且由BeanContext统一管理Bean实例，Spring的部分思想借鉴了JavaBeans
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);
        /**
         * java.beans.PropertyDescriptor[name=id; propertyType=int; readMethod=public int org.imzdong.study.spring.ioc.beans.Person.getId(); writeMethod=public void org.imzdong.study.spring.ioc.beans.Person.setId(int)]
         * java.beans.PropertyDescriptor[name=name; propertyType=class java.lang.String; readMethod=public java.lang.String org.imzdong.study.spring.ioc.beans.Person.getName(); writeMethod=public void org.imzdong.study.spring.ioc.beans.Person.setName(java.lang.String)]
         */
        Stream.of(beanInfo.getPropertyDescriptors())
                .forEach(propertyDescriptor -> {
                    //System.out.println(propertyDescriptor);
                    //允许添加属性编辑器-PropertyEditor
                    String name = propertyDescriptor.getName();
                    if("id".equals(name)){
                        propertyDescriptor.setPropertyEditorClass(StringToIntegerPropertyEditor.class);
                        //propertyDescriptor.createPropertyEditor();
                    }
                });
    }
    static class StringToIntegerPropertyEditor extends PropertyEditorSupport{
        public void setAsText(String text) throws java.lang.IllegalArgumentException {
            Integer value = Integer.valueOf(text);
            setValue(value);
        }
    }
}
