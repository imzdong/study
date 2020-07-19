package org.imzdong.study.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatis两种方式生成model:
 * 1、java原生api支持
 * 2、maven插件：mybatis-generator:generate
 */
public class Generator {

    public static void main(String[] args) throws Exception{
        List<String> warnings = new ArrayList<String>();
        InputStream resourceAsStream = Generator.class.getResourceAsStream("/generator_config_java.xml");
        //File configFile = new File(resourceAsStream);com.mysql.cj.jdbc.Driver
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(resourceAsStream);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        System.out.println(warnings);
    }
}
