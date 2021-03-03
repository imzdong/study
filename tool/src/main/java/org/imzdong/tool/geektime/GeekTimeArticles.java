package org.imzdong.tool.geektime;

import com.alibaba.fastjson.JSONObject;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
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
        String url = GeekTimeConstant.articlesUrl;;
        Map<String, String> headerMap = GeekTimeConstant.headers;
        headerMap.put(GeekTimeConstant.cookie,cookie);
        Headers headers = Headers.of(headerMap);
        String resp = null;
        try {
            //{"error":{"msg":"无效的文章ID","code":-2202},"extra":{"internal":[]},"data":[],"code":-1}
            JSONObject bodyJson = new JSONObject();
            bodyJson.put("cid", columnId);
            bodyJson.put("size", size);
            bodyJson.put("prev", prev);
            bodyJson.put("order", order);
            RequestBody body = FormBody.create(bodyJson.toJSONString(), MediaType.parse("text"));
            resp = OkHttpUtils.get(url, headers, body);
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
