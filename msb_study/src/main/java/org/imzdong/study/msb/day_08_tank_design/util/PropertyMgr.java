package org.imzdong.study.msb.day_08_tank_design.util;

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

    private PropertyMgr(){}

    public static Object get(String key){
        return properties.get(key);
    }

    public static Integer getInt(String key){
        return Integer.parseInt((String)properties.get(key));
    }
}
