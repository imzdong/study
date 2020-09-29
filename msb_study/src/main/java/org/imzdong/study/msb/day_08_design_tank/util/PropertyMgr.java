package org.imzdong.study.msb.day_08_design_tank.util;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {

    private static Properties properties = new Properties();

    static {
        try {
            properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object get(String key){
        return properties.get(key);
    }
}
