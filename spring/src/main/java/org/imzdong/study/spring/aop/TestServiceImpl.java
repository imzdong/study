package org.imzdong.study.spring.aop;

import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Winter
 * @time: 2019年12月17日, 0017 14:29
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    @LogAnnotation
    public String sayHello(String name) {
        return name+":sayHello";
    }
}
