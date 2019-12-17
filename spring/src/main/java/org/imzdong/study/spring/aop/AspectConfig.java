package org.imzdong.study.spring.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @description:
 * @author: Winter
 * @time: 2019年12月17日, 0017 14:39
 */
@Configuration
@ComponentScan(basePackages="org.imzdong.study.spring.aop")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectConfig {
}
