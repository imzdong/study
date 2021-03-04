package org.imzdong.tool.geektime;

import com.alibaba.fastjson.JSONObject;
import org.imzdong.tool.util.OkHttpUtils;

import java.io.IOException;
import java.util.Map;

public class GeekTimeData {

    private String cookie;

    public GeekTimeData(String cookie){
        this.cookie = cookie;
    }

    public String getArticleCount() {
        return getData();
    }

    private String getData(){
        String url = GeekTimeConstant.dataUrl;
        Map<String, String> headerMap = GeekTimeConstant.headers;
        headerMap.put(GeekTimeConstant.cookie, cookie);
        try {
            String resp = OkHttpUtils.http(url, OkHttpUtils.getHeaders(headerMap), null);
            JSONObject result = JSONObject.parseObject(resp);
            System.out.println(result.get("error"));
            if(result.containsKey("code")&&"0".equals(result.getString("code"))){
                JSONObject data = result.getJSONObject("data");
                return data.getString("columns_count");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
