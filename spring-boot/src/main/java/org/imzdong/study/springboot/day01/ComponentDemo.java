package org.imzdong.study.springboot.day01;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ComponentDemo {

    @Bean
    public String creatStr(){
        return "String";
    }
}
