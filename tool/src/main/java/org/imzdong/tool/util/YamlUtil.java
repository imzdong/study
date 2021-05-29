package org.imzdong.tool.util;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YamlUtil {

    public static Map<String, String> getYamlProperties(String path){
        InputStream resourceAsStream = YamlUtil.class.getClassLoader().getResourceAsStream(path);
        Yaml yaml = new Yaml();
        Map<String, String> load = yaml.load(resourceAsStream);
        return load;
    }

    public static String getYamlValueByProperty(String path, String key){
        Map<String, String> yamlProperties = getYamlProperties(path);
        if(yamlProperties != null){
            return yamlProperties.get(key);
        }
        return null;
    }
}
