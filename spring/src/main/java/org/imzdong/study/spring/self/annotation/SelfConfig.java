package org.imzdong.study.spring.self.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SelfConfig {

    @Bean
    public GoodMan getGoodMan(){
        return new GoodMan("SelfConfig");
    }
}
