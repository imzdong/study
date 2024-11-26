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

    private static final String basePath = "D:\\Downloads\\geektime\\1126\\";
    private static final String courseHtmlDirName = basePath + "org\\";
    public static final String CUSTOM_COOKIE = "_ga=GA1.2.862048808.1679818512; LF_ID=6b93249-de85e48-3d28884-6df1e9f; mantis5539=9950a03d0c64485f87fba01a33178d1c@5539; _ga_MTX5SQH9CV=GS1.2.1720104229.1.0.1720104229.0.0.0; MEIQIA_TRACK_ID=2NXoVZD67mAsW8zVLnuwPk4FD0h; MEIQIA_VISIT_ID=2immhkfMP4Zxn34VIrlDfO4P9AD; GCID=1194a91-98492ae-6bdb82a-5356ec1; GRID=1194a91-98492ae-6bdb82a-5356ec1; gksskpitn=0dfc3f21-4c7d-45e5-ab23-d0791a1adebc; Hm_lvt_022f847c4e3acd44d4a2481d9187f1e6=1732430516,1732546334; HMACCOUNT=593342EE700F4024; Hm_lvt_59c4ff31a9ee6263811b23eb921a5083=1732430516,1732546334; _gid=GA1.2.316603876.1732546334; _ga_JW698SFNND=GS1.2.1732628555.9.1.1732628558.0.0.0; gk_process_ev={%22count%22:2%2C%22utime%22:1732628558246%2C%22referrer%22:%22https://time.geekbang.org/course/intro/100767801%22%2C%22target%22:%22page_geektime_login%22%2C%22referrerTarget%22:%22page_geektime_login%22}; GCESS=BgkBAQUEAAAAAAoEAAAAAAwBAQIEV9BFZwYE60hsLAEIYkYPAAAAAAAHBE03SosLAgYACAEDBAQAjScADQEBAwRX0EVn; tfstk=ftnSa2mHp_fWVzLy59p4GDSO5NqIPXtwR9wKIvIPpuE8AWGKiazUTvCCAflkKLDE4qGIdfV8YuoyA2Zo04SUaQqIpfrB_C-wbYDu-kdw_9thZEqTQ8QdTWeYMNfPfrKwbYDRvpkB6hoPmAua_WELyyUAHS2hwkeLJKNYdRbdwXhKHKwQFJB8vgQvDReLvWhLvKMxtFRbnUN-FKR8l_fffjD8GGMmm8C31hV5vMobFmNteFIdvmw7yDlYgTBbz2HgS0kyAiqrCqEYFXK9woHtlAzSVCsztyijN2GyLw2jRvngql6p2xZ7NPn7r9js6xMK7ri2Bhq7273UqvW6axiSaYmjL9QYVuub50Z9j_FEuAiQdDAHcfM-WX3C4rIa1EyFRtaGA-NwhK_h-xPHfDStqjLzy-2qbK9fNw48n-NwhK_h-ze03PJXh__h.; _gat=1; Hm_lpvt_59c4ff31a9ee6263811b23eb921a5083=1732628892; Hm_lpvt_022f847c4e3acd44d4a2481d9187f1e6=1732628892; __tea_cache_tokens_20000743={%22web_id%22:%227440732456057097485%22%2C%22user_unique_id%22:%221001058%22%2C%22timestamp%22:1732628892230%2C%22_type_%22:%22default%22}; _ga_03JGDGP9Y3=GS1.2.1732628552.14.1.1732628892.0.0.0; SERVERID=3431a294a18c59fc8f5805662e2bd51e|1732628896|1732628554";
    public static final String courseOut = basePath + "out\\";

    public static void main(String[] args) throws Exception{
        GeekTimeConstant.headers.put("Cookie", CUSTOM_COOKIE);
        String cid = "100036501";

        new File(courseHtmlDirName).mkdirs();
        new File(courseOut).mkdirs();

        //startBook(cid);
        //testGetArticle();
        /*List<CourseChapter> chapters = getChapters("100770601");
        System.out.println(chapters.size());*/
        testMobi();
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
                    ImageParser parser = new ImageParser(courseHtmlDirName+"\\images");
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
