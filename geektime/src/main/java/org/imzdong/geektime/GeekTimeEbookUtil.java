package org.imzdong.geektime;

import static org.imzdong.geektime.TocUtil.escapeFileName;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.samskivert.mustache.Mustache;
import lombok.extern.slf4j.Slf4j;
import org.imzdong.geektime.ebook.EbookMaker;
import org.imzdong.geektime.ebook.Toc;
import org.imzdong.geektime.model.CourseChapter;
import org.imzdong.geektime.model.CourseInfo;
import org.imzdong.geektime.util.OkHttpUtils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Slf4j
public class GeekTimeEbookUtil {

    private static final String fileName = Objects.requireNonNull(GeekTimeEbookUtil.class.getClassLoader().getResource("ebbok-article.mustache")).getPath();
    // 加载模板文件
    private static final FileReader templateFile;

    static {
        try {
            templateFile = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String courseHtmlDirName = "D:\\Download\\geektime\\test\\";
    public static final String CUSTOM_COOKIE = "";


    public static void main(String[] args) throws Exception{
        GeekTimeConstant.headers.put("Cookie", CUSTOM_COOKIE);
        startBook();
        //testGetArticle();
        /*List<CourseChapter> chapters = getChapters("100770601");
        System.out.println(chapters.size());*/
        //testMobi();
    }

    private static void testGetArticle() {
        //780923 781435
        GeekTimeArticle geekTimeArticle = new GeekTimeArticle("780923");
        String content = geekTimeArticle.getContent();
        System.out.printf(content);
    }

    private static void testMobi(){
        EbookMaker.makeEbook(courseHtmlDirName, "D:\\Download\\geektime\\out", "独行", "mobi");
    }

    private static void startBook() throws IOException {
        String cid = "100770601";
        new File(courseHtmlDirName).mkdirs();
        CourseInfo info = getInfo(cid);
        downLoadCover(info.getCover());
        List<CourseChapter> chapters = getChapters(cid);
        List<String> articles = listArticles(cid);
        Toc toc = TocUtil.fillToc(info, chapters, articles, courseHtmlDirName);
        TocUtil.generateMarkdownFile(courseHtmlDirName+"toc.md", toc);
        EbookMaker.makeEbook(courseHtmlDirName, "D:\\Download\\geektime\\out", info.getAuthorName(), "mobi");
    }

    private static CourseInfo getInfo(String cid) throws IOException {
        String url = GeekTimeConstant.introUrl;
        Map<String, String> headerMap = GeekTimeConstant.headers;
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("cid", cid);
        String resp = OkHttpUtils.http(url, OkHttpUtils.getHeaders(headerMap),
                OkHttpUtils.getRequestBody(OkHttpUtils.TEXT, bodyJson.toJSONString()));
        JSONObject result = JSONObject.parseObject(resp);
        if(result != null&&result.containsKey("code")&&"0".equals(result.getString("code"))){
            JSONObject data = result.getJSONObject("data");
            System.out.println(data.toJSONString());
            CourseInfo courseInfo = new CourseInfo();
            courseInfo.setColumnTitle(data.getString("column_title"));
            courseInfo.setAuthorName(data.getString("author_name"));
            courseInfo.setCover(data.getString("column_poster"));
            courseInfo.setArticleCount(data.getInteger("article_count"));
            return courseInfo;
            //column_title 课程名称
        }else {
            log.error("课程详情返回code：{}，返回错误信息：{}",
                    result != null?result.getString("code"):resp,
                    result != null?result.getString("error"):"");
            throw new RuntimeException("获取课程信息失败");
        }
    }

    private static List<CourseChapter> getChapters(String cid) throws IOException {
        String url = GeekTimeConstant.chaptersUrl;
        Map<String, String> headerMap = GeekTimeConstant.headers;
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("cid", cid);
        String resp = OkHttpUtils.http(url, OkHttpUtils.getHeaders(headerMap),
                OkHttpUtils.getRequestBody(OkHttpUtils.TEXT, bodyJson.toJSONString()));
        JSONObject result = JSONObject.parseObject(resp);
        if(result != null&&result.containsKey("code")&&"0".equals(result.getString("code"))){
            JSONArray datas = result.getJSONArray("data");
            System.out.println(datas.toJSONString());
            List<CourseChapter> courseChapters = new ArrayList<>();
            for (Object dataObj : datas) {
                JSONObject data = (JSONObject) dataObj;
                CourseChapter courseChapter = new CourseChapter();
                courseChapter.setTitle(data.getString("title"));
                courseChapter.setArticleCount(data.getInteger("article_count"));
                courseChapters.add(courseChapter);
            }
            return courseChapters;
            //column_title 课程名称
        }else {
            log.error("课程详情返回code：{}，返回错误信息：{}",
                    result != null?result.getString("code"):resp,
                    result != null?result.getString("error"):"");
            throw new RuntimeException("获取chapters失败");
        }
    }

    private static void downLoadCover(String coverPath){
        try {
            // 图片的 URL 地址
            String imageUrl = coverPath;
            // 保存图片的本地路径
            Path destinationPath = Path.of(courseHtmlDirName + "cover.jpg");

            // 创建 URL 对象
            URL url = new URL(imageUrl);

            // 打开连接并获取输入流
            try (InputStream in = url.openStream()) {
                // 将输入流的内容复制到本地文件
                Files.copy(in, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            }

            System.out.println("图片下载完成！");
        } catch (Exception e) {
            throw new RuntimeException("cover download failed");
        }
    }

    private static List<String> listArticles(String cid) throws IOException {
        GeekTimeArticles geekTimeArticles = new GeekTimeArticles(cid);
        List<GeekTimeArticle> articleList = geekTimeArticles.getArticleList();
        System.out.println(articleList);
        List<String> articleNames = new ArrayList<>();
        if(articleList != null){
            log.info("获取到文章列表：{}", articleList.size());
            for (GeekTimeArticle article : articleList) {
                String content = article.getContent();
                if(content != null){
                    String articleTitle = escapeFileName(article.getArticleTitle());
                    ImageParser parser = new ImageParser(courseHtmlDirName);
                    content = parser.parseImage(content);
                    article2Html(articleTitle, content, courseHtmlDirName);
                    articleNames.add(articleTitle);
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
        return articleNames;
    }

    public static String article2Html(String articleTitle, String content,
                                       String courseHtmlDirName)
            throws IOException {
        articleTitle = replace(articleTitle);
        String articlePath = courseHtmlDirName + File.separator + articleTitle + ".html";
        log.info("转换文章为html格式开始：{}，路径：{}", articleTitle, articlePath);
        String con = replaceTemplate(articleTitle, content);
        FileWriter fileWriter = new FileWriter(articlePath);
        fileWriter.write(con);
        fileWriter.close();
        return articlePath;
    }



    private static String replaceTemplate(String title, String content){
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <title>"+title+"</title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>"+title+"</h1>\n" +
                "\n" +
                 content +"\n" +
                "\n" +
                "</body>\n" +
                "</html>";
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
