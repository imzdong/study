package org.imzdong.study.springboot.day02enable;

import org.imzdong.study.springboot.day01.ConfigurationDemo;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({CommonClass.class, ConfigurationDemo.class, ImportSelectorImpl.class,ImportBeanImpl.class})
public @interface EnableClass {
}
