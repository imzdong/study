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

    private static final String basePath = "D:\\Download\\geektime\\1128\\";
    private static final String courseHtmlDirName = basePath + "org\\";
    public static final String CUSTOM_COOKIE = "LF_ID=544be84-ccd118c-86b410f-8db7582; _ga=GA1.2.841203803.1703505414; gksskpitn=f3445897-a1a8-47ce-8494-16331879fd50; Hm_lvt_59c4ff31a9ee6263811b23eb921a5083=1732506342; HMACCOUNT=38F5FD8E33B82642; Hm_lvt_022f847c4e3acd44d4a2481d9187f1e6=1732506342; GCID=8bd4688-df0d6e7-0f970e9-c9094f2; GRID=8bd4688-df0d6e7-0f970e9-c9094f2; _gid=GA1.2.1867160501.1732678293; _ga_JW698SFNND=GS1.2.1732678295.4.1.1732678564.0.0.0; gk_process_ev={%22count%22:2%2C%22utime%22:1732678565082%2C%22referrer%22:%22https://time.geekbang.org/%22%2C%22target%22:%22page_geektime_login%22%2C%22referrerTarget%22:%22page_geektime_login%22}; GCESS=BgIEqZNGZwgBAwoEAAAAAAsCBgANAQEGBE0lh2gFBAAAAAADBKmTRmcEBACNJwAMAQEBCGJGDwAAAAAACQEBBwQM8TcZ; tfstk=f-HSa2908ab7MaOy1MK4l4-1162IN29wOMZKjDBPv8e8dygKo_PU8DQCdVukx3mEUjgIAV48L8kydXwo3bWUzU2IvVyB7FJwQ0munJLw7EduHW2T2yCR8uBYH1bPCSpwQ0mRpHoBWdkPiy877yeLwWFADrqF2JEL9naYAlfdy23KDnZQVkERwJBxDlqLpy3LpnixNjs_AQaoVn8uIab56ig8l9B1juVRO01FBOH7V7MK2rZ7VxZ7NPNfPDSoeqoKUYVDMswn0fgI9VLVazh_fqNZNF6S54qKAk3WIGaxFDMbZjSNCmNQPjH8GMBKX0czMXgWP_4-aqNgDSIBIouak0D-GHbx2Vz7eoF2BhiY9jkusY8AFRhi48lKWE5zyfZd4UXa5N-GOiNGdoawcn1htmzHCvPBipT4woq7gntfyXV8moawcn1ht7E0Vl-Xcahh.; _gat=1; Hm_lpvt_022f847c4e3acd44d4a2481d9187f1e6=1732679235; Hm_lpvt_59c4ff31a9ee6263811b23eb921a5083=1732679235; _ga_03JGDGP9Y3=GS1.2.1732678294.16.1.1732679235.0.0.0; __tea_cache_tokens_20000743={%22web_id%22:%227441058074707792906%22%2C%22user_unique_id%22:%221001058%22%2C%22timestamp%22:1732679235332%2C%22_type_%22:%22default%22}; SERVERID=1fa1f330efedec1559b3abbcb6e30f50|1732679236|1732678290";
    public static final String courseOut = basePath + "out\\";

    public static void main(String[] args) throws Exception{
        GeekTimeConstant.headers.put("Cookie", CUSTOM_COOKIE);
        String cid = "100617601";

        new File(courseHtmlDirName).mkdirs();
        new File(courseOut).mkdirs();

        startBook(cid);
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
        EbookMaker.makeEbook(courseHtmlDirName, courseOut, "王健", "mobi");
    }

    private static void startBook(String cid) throws IOException {
        CourseInfo info = getInfo(cid);
        downLoadCover(info.getCover());
        List<CourseChapter> chapters = getChapters(cid);
        List<String> articles = listArticles(cid);
        Toc toc = TocUtil.fillToc(info, chapters, articles, courseHtmlDirName);
        TocUtil.generateMarkdownFile(courseHtmlDirName+"toc.md", toc);
        EbookMaker.makeEbook(courseHtmlDirName, courseOut, info.getAuthorName(), "mobi");
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

}
