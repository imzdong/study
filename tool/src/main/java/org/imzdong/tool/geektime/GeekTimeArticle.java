package org.imzdong.tool.geektime;

import com.alibaba.fastjson.JSONObject;
import org.imzdong.tool.util.OkHttpUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 极客时间课程文章
 * @author winter
 * @date 2021-03-04
 */
public class GeekTimeArticle implements Comparable<GeekTimeArticle>{

    private String articleId;
    private String cookie;
    //article_title
    private String articleTitle;
    //audio_download_url
    private String audioDownloadUrl;
    //article_cover
    private String articleCover;
    private String articleContent;
    //author_name
    private String authorName;
    //article_ctime
    private String articleCtime;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");

    public GeekTimeArticle(String articleId, String cookie){
        this.articleId = articleId;
        this.cookie = cookie;
    }

    public String getContent(){
        String url = GeekTimeConstant.articleUrl;
        Map<String, String> headerMap = GeekTimeConstant.headers;
        headerMap.put(GeekTimeConstant.cookie,cookie);
        //{"id":"6458","include_neighbors":true,"is_freelyread":true}
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("id", articleId);
        bodyJson.put("include_neighbors", true);
        bodyJson.put("is_freelyread", true);
        String resp = null;
        try {
            //{"error":{"msg":"无效的文章ID","code":-2202},"extra":{"internal":[]},"data":[],"code":-1}
            resp = OkHttpUtils.http(url, OkHttpUtils.getHeaders(headerMap),
                    OkHttpUtils.getRequestBody(OkHttpUtils.TEXT, bodyJson.toJSONString()));
            JSONObject result = JSONObject.parseObject(resp);
            if(result.containsKey("code")&&"0".equals(result.getString("code"))){
                JSONObject data = result.getJSONObject("data");
                articleTitle = data.getString("article_title");
                audioDownloadUrl = data.getString("audio_download_url");
                articleContent = data.getString("article_content");
                articleCover = data.getString("article_cover");
                authorName = data.getString("author_name");
                articleCtime = simpleDateFormat.format(data.getDate("article_ctime"));
                return articleContent;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getAudioDownloadUrl() {
        return audioDownloadUrl;
    }

    public String getArticleCover() {
        return articleCover;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setArticleCtime(String articleCtime) {
        this.articleCtime = articleCtime;
    }

    @Override
    public int compareTo(@NotNull GeekTimeArticle o) {
        return this.articleId.compareTo(o.articleId);
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public void setArticleCover(String articleCover) {
        this.articleCover = articleCover;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getArticleCtime() {
        return articleCtime;
    }

    public String getArticleContent() {
        return articleContent;
    }
}
