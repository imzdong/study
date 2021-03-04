package org.imzdong.tool.geektime;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.imzdong.tool.util.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 极客时间课程列表
 * @author winter
 * @date 2021-03-04
 */
public class GeekTimeCourses {

    private int prev;
    private String cookie;
    private int size;

    public GeekTimeCourses(int prev, int size, String cookie){
        this.prev = prev;
        this.size = size;
        this.cookie = cookie;
    }

    public List<GeekTimeCourse> getCourses(){
        String url = GeekTimeConstant.courseUrl;
        Map<String, String> headerMap = GeekTimeConstant.headers;
        headerMap.put(GeekTimeConstant.cookie, cookie);
        //{"desc":true,"expire":1,"last_learn":0,"learn_status":0,"prev":0,"size":20,
        // "sort":1,"type":"","with_learn_count":1}
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("desc", true);
        bodyJson.put("expire", 1);
        bodyJson.put("last_learn", 0);
        bodyJson.put("learn_status", 0);
        bodyJson.put("prev", prev);
        bodyJson.put("size", size);
        bodyJson.put("sort", 1);
        bodyJson.put("type", "");
        bodyJson.put("with_learn_count", 1);
        try {
            String resp = OkHttpUtils.http(url, OkHttpUtils.getHeaders(headerMap),
                    OkHttpUtils.getRequestBody(OkHttpUtils.JSON, bodyJson.toJSONString()));
            JSONObject result = JSONObject.parseObject(resp);
            System.out.println(result.get("error"));
            if(result.containsKey("code")&&"0".equals(result.getString("code"))){
                JSONObject data = result.getJSONObject("data");
                JSONArray products = data.getJSONArray("products");
                if(products != null && products.size() > 0){
                    List<GeekTimeCourse> list = new ArrayList<>();
                    products.forEach(m->{
                        JSONObject course = (JSONObject) m;
                        GeekTimeCourse geekTimeCourse = new GeekTimeCourse();
                        geekTimeCourse.setTitle(course.getString("title"));
                        geekTimeCourse.setId(course.getString("id"));
                        list.add(geekTimeCourse);
                    });
                    return list;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
