package org.imzdong.geektime.util;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class YamlUtil {

    public static Map<String, Object> getYamlProperties(String path){
        InputStream resourceAsStream = YamlUtil.class.getClassLoader().getResourceAsStream(path);
        //Yaml yaml = new Yaml();
        Map<String, Object> load = new HashMap<>();//yaml.load(resourceAsStream);
        return load;
    }

    public static String getYamlValueByProperty(String path, String key){
        Map<String, Object> yamlProperties = getYamlProperties(path);
        if(yamlProperties != null){
            return yamlProperties.get(key).toString();
        }
        return null;
    }
}
