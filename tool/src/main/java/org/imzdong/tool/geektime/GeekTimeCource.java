package org.imzdong.tool.geektime;

import com.alibaba.fastjson.JSONObject;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.imzdong.tool.util.OkHttpUtils;

import java.io.IOException;
import java.util.Map;

public class GeekTimeCource {

    private String articleId;
    private String cookie;
    //article_title
    private String articleTitle;
    //audio_download_url
    private String audioDownloadUrl;

    public GeekTimeCource(String articleId, String cookie){
        this.articleId = articleId;
        this.cookie = cookie;
    }

    public String getArticleContent(){
        String url = GeekTimeConstant.articleUrl;;
        Map<String, String> headerMap = GeekTimeConstant.headers;
        headerMap.put(GeekTimeConstant.cookie,cookie);
        Headers headers = Headers.of(headerMap);
        //{"id":"6458","include_neighbors":true,"is_freelyread":true}
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("id", articleId);
        bodyJson.put("include_neighbors", true);
        bodyJson.put("is_freelyread", true);
        RequestBody body = FormBody.create(bodyJson.toJSONString(), MediaType.parse("text"));
        String resp = null;
        try {
            //{"error":{"msg":"无效的文章ID","code":-2202},"extra":{"internal":[]},"data":[],"code":-1}
            resp = OkHttpUtils.get(url, headers, body);
            JSONObject result = JSONObject.parseObject(resp);
            if(result.containsKey("code")&&"0".equals(result.getString("code"))){
                JSONObject data = result.getJSONObject("data");
                articleTitle = data.getString("article_title");
                audioDownloadUrl = data.getString("audio_download_url");
                return data.getString("article_content");
            }else {
                return result.getString("error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getAudioDownloadUrl() {
        return audioDownloadUrl;
    }
}
