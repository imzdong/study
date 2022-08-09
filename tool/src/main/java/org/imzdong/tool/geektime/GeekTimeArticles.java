package org.imzdong.tool.geektime;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.imzdong.tool.util.OkHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 极客时间课程文章列表
 * @author winter
 * @date 2021-03-04
 */
public class GeekTimeArticles {

    private final static Logger logger = LoggerFactory.getLogger(GeekTimeArticles.class);

    private String cid;

    public GeekTimeArticles(String cid){
        this.cid = cid;
    }

    public List<GeekTimeArticle> getArticleList(){
        String url = GeekTimeConstant.articlesUrl;
        Map<String, String> headerMap = GeekTimeConstant.headers;
        try {
            // {
            //  "cid": "100084301",
            //  "size": 500,
            //  "prev": 0,
            //  "order": "earliest",
            //  "sample": false
            //}
            /*String articles = "{\"cid\":100006601,\"size\":500,\"prev\":0,\"order\":\"earliest\",\"sample\":false," +
                    "\"chapter_ids\":[\"359\",\"360\",\"361\",\"362\",\"363\",\"364\",\"365\",\"366\",\"1310\"]}";*/
            JSONObject bodyJson = new JSONObject();//JSONObject.parseObject(articles);
            bodyJson.put("cid", cid);
            bodyJson.put("size", 500);
            bodyJson.put("prev", 0);
            bodyJson.put("order", "earliest");
            bodyJson.put("sample", false);
            String resp = OkHttpUtils.http(url, OkHttpUtils.getHeaders(headerMap),
                    OkHttpUtils.getRequestBody(OkHttpUtils.TEXT, bodyJson.toJSONString()));
            JSONObject result = JSONObject.parseObject(resp);
            if(result != null&&result.containsKey("code")&&"0".equals(result.getString("code"))){
                JSONObject data = result.getJSONObject("data");
                JSONArray list = data.getJSONArray("list");
                List<GeekTimeArticle> geekTimeArticles = new ArrayList<>(list.size());
                list.forEach(m->{
                    JSONObject articleJson = (JSONObject) m;
                    GeekTimeArticle timeArticle = new GeekTimeArticle(articleJson.getString("id"));
                    geekTimeArticles.add(timeArticle);
                });
                return geekTimeArticles;
            }else {
                logger.error("获取课程文章列表返回code：{}，返回错误信息：{}",
                        result != null?result.getString("code"):resp,
                        result != null?result.getString("error"):"");
            }
        } catch (IOException e) {
            logger.error("获取课程文章列表异常：", e);
        }
        return null;
    }

}
