package org.imzdong.study.spring.aop;

import java.lang.annotation.*;

/**
 * @description:
 * @author: Winter
 * @time: 2019年12月17日, 0017 14:42
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface LogAnnotation {
}
