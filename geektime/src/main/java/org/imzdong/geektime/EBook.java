package org.imzdong.geektime;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.samskivert.mustache.Mustache;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.imzdong.geektime.util.OkHttpUtils;
import org.imzdong.geektime.util.TemplateUtil;

import java.io.*;
import java.util.*;

@Slf4j
public class EBook {

    private static final String fileName = Objects.requireNonNull(EBook.class.getClassLoader().getResource("ebbok-article.mustache")).getPath();
    // 加载模板文件
    private static final FileReader templateFile;

    static {
        try {
            templateFile = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getInfo(String cid) throws IOException {
        String url = GeekTimeConstant.introUrl;
        Map<String, String> headerMap = GeekTimeConstant.headers;
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
        String resp = OkHttpUtils.http(url, OkHttpUtils.getHeaders(headerMap),
                OkHttpUtils.getRequestBody(OkHttpUtils.TEXT, bodyJson.toJSONString()));
        JSONObject result = JSONObject.parseObject(resp);
        if(result != null&&result.containsKey("code")&&"0".equals(result.getString("code"))){
            JSONObject data = result.getJSONObject("data");
            System.out.println(data.toJSONString());

            //column_title 课程名称
        }else {
            log.error("课程详情返回code：{}，返回错误信息：{}",
                    result != null?result.getString("code"):resp,
                    result != null?result.getString("error"):"");
        }
    }

    private static JSONObject getChapters(String cid) throws IOException {
        String url = GeekTimeConstant.chaptersUrl;
        Map<String, String> headerMap = GeekTimeConstant.headers;
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
        String resp = OkHttpUtils.http(url, OkHttpUtils.getHeaders(headerMap),
                OkHttpUtils.getRequestBody(OkHttpUtils.TEXT, bodyJson.toJSONString()));
        JSONObject result = JSONObject.parseObject(resp);
        if(result != null&&result.containsKey("code")&&"0".equals(result.getString("code"))){
            JSONObject data = result.getJSONObject("data");
            System.out.println(data.toJSONString());
            return data;
            //column_title 课程名称
        }else {
            log.error("课程详情返回code：{}，返回错误信息：{}",
                    result != null?result.getString("code"):resp,
                    result != null?result.getString("error"):"");
            throw new RuntimeException("获取chapters失败");
        }
    }

    public static void main(String[] args) throws Exception{
        String cid = "100770601";
        getInfo(cid);
        //listArticles(cid);
    }

    private void makeBook(){

    }

    private static void listArticles(String cid) throws IOException {
        GeekTimeArticles geekTimeArticles = new GeekTimeArticles(cid);
        List<GeekTimeArticle> articleList = geekTimeArticles.getArticleList();
        System.out.println(articleList);
        String courseHtmlDirName = "D:\\Downloads\\geektime\\"+ cid;
        new File(courseHtmlDirName).mkdirs();
        if(articleList != null){
            log.info("获取到文章列表：{}", articleList.size());
            for (GeekTimeArticle article : articleList) {
                String content = article.getContent();
                if(content != null){
                    article2Html(article.getArticleTitle(), content, courseHtmlDirName);
                }else {
                    log.info("未获取到文章内容");
                }
                try {
                    Thread.sleep(1000L*3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else {
            log.info("未获取到文章列表");
        }
    }

    private static String article2Html(String articleTitle, String content,
                                       String courseHtmlDirName)
            throws IOException {
        Map<String, String> map = new HashMap<>();
        articleTitle = replace(articleTitle);
        String articlePath = courseHtmlDirName + File.separator + articleTitle + ".html";
        log.info("转换文章为html格式开始：{}，路径：{}", articleTitle, articlePath);
        map.put("title", articleTitle);
        map.put("content", content);
        replaceTemplate(map, new FileWriter(articlePath));
        return articlePath;
    }

    private static void replaceTemplate(Map<String, String> data, Writer out){
            Mustache.compiler().compile(templateFile).execute(data, out);
            System.out.println("HTML file generated successfully.");
    }

    private static String replace(String dirPath){
        /*
         * windows下文件名中不能含有：\ / : * ? " < > | 英文的这些字符 ，这里使用"."、"'"进行替换。
         * \/:?| 用.替换
         * "<> 用'替换
         */
        dirPath = dirPath.replaceAll("[/\\\\:*?|]", "");
        dirPath = dirPath.replaceAll("[\"<>]", "");
        dirPath = dirPath.replaceAll("\\s*", "");
        return dirPath;
    }

}
