package org.imzdong.tool.geektime;

import com.alibaba.fastjson.JSONObject;
import org.imzdong.tool.util.OkHttpUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 极客时间课程列表
 */
public class GeekTimeArticles {

    private String columnId;
    private String order;
    private String cookie;
    private int size;
    private int prev;

    public GeekTimeArticles(String columnId, int size, int prev, String order, String cookie){
        this.columnId = columnId;
        this.order = order;
        this.cookie = cookie;
        this.size = size;
        this.prev = prev;
    }

    public List<String> getArticleList(){
        String url = GeekTimeConstant.articlesUrl;
        Map<String, String> headerMap = GeekTimeConstant.headers;
        headerMap.put(GeekTimeConstant.cookie,cookie);
        String resp = null;
        try {
            JSONObject bodyJson = new JSONObject();
            bodyJson.put("cid", columnId);
            bodyJson.put("size", size);
            bodyJson.put("prev", prev);
            bodyJson.put("order", order);
            resp = OkHttpUtils.http(url, OkHttpUtils.getHeaders(headerMap),
                    OkHttpUtils.getRequestBody(OkHttpUtils.TEXT, bodyJson.toJSONString()));
            System.out.println(resp);
            JSONObject result = JSONObject.parseObject(resp);
            if(result.containsKey("code")&&"0".equals(result.getString("code"))){
                JSONObject data = result.getJSONObject("data");
            }else {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
