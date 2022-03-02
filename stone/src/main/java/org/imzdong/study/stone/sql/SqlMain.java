package org.imzdong.study.stone.sql;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * sql拼接
 * @author admin
 * @date 2021/5/29 下午7:52
 */
public class SqlMain {

    private final static String QUERY = "query";
    private final static String BOOL = "bool";
    private final static String MUST = "must";
    private final static String SHOULD = "should";
    private final static String AND = "and";
    private final static String SPACE = " ";

    public static void main(String[] args) {
        /**
          {"query":{
              "bool":{
                  "must":{
                      "id":{"gt":"123"},
                      ...
                  },
                  "should":{
                  }
              }
          }}
         */
        String querySql = "";
        StringBuilder sqlStr = new StringBuilder("select userId from table where 1=1");
        JSONObject sqlJson = JSONObject.parseObject(querySql);
        JSONObject boolJson = sqlJson.getJSONObject(QUERY);
        if(boolJson.containsKey(MUST)){
            JSONObject must = boolJson.getJSONObject(MUST);
            Set<String> keySet = must.keySet();
            for(String key:keySet){
                Map<String, Object> fieldMap = must.getObject(key, Map.class);

                //sqlStr.append(AND).append(key).append(SPACE).append()
            }
        }
    }

    private boolean checkSql(String querySql){
        JSONObject sqlJson = JSONObject.parseObject(querySql);
        if(!sqlJson.containsKey("query")){
            return false;
        }
        JSONObject boolJson = sqlJson.getJSONObject("query");
        if(!boolJson.containsKey("must") && !boolJson.containsKey("should")){
            return false;
        }
        return true;
    }

}
