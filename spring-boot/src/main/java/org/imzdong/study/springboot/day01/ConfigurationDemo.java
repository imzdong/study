package org.imzdong.study.springboot.day01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @description:
 * @author: Winter
 * @time: 2019年11月12日, 0012 13:45
 * @des: 相当于在xml配置
 *       <bean id="createDate"
 *       class="org.imzdong.springboot.day01.Date">
 */
@Configuration
public class ConfigurationDemo {

    @Bean
    public Date createDate(){
        return new Date();
    }
}
