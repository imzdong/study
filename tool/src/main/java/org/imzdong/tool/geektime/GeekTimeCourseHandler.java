package org.imzdong.tool.geektime;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.imzdong.tool.util.Itext7HtmlPdfUtil;
import org.imzdong.tool.util.PdfUtils;
import org.imzdong.tool.util.TemplateUtil;
import org.imzdong.tool.util.YamlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 极客时间获取课程信息启动类
 * 仅仅修改yml配置类
 * @author zhoud
 * @date 2021-03-06
 */
public class GeekTimeCourseHandler {

    public static void main(String[] args) throws Exception {
        GeekTimeCourseHandler handler = new GeekTimeCourseHandler();
        handler.start();
    }

    private final static Logger logger = LoggerFactory.getLogger(GeekTimeCourseHandler.class);
    private Configuration init;
    private String separator;
    private String cookie;
    private String outPath;

    public GeekTimeCourseHandler(){
        init();
    }

    private void init() {
        String yamlPath = "geekTime.yaml";
        Map<String, String> yamlProperties = YamlUtil.getYamlProperties(yamlPath);
        cookie = yamlProperties.get("cookie");
        outPath = yamlProperties.get("outPath");
        Properties properties = System.getProperties();
        separator = properties.getProperty("file.separator");
        String fileName = this.getClass().getClassLoader().getResource(GeekTimeConstant.templateDir).getPath();
        init = TemplateUtil.init(fileName);
    }

    /**
     * 启动任务开始
     * @throws IOException
     * @throws TemplateException
     */
    public void start() throws IOException, TemplateException {
        if(cookie == null || init == null){
            throw new RuntimeException("初始化失败");
        }
        GeekTimeCourse course = new GeekTimeCourse();
        course.setId("100084301");
        course.setTitle("Redis 源码剖析与实战");
        String title = course.getTitle();
        logger.info("获取课程：{}：开始。", title);
        String courseDirName = outPath + separator + title;
        String courseHtmlDirName = courseDirName + separator + "html";
        String coursePdfDirName = courseDirName + separator + "pdf";
        String courseMergePdfDirName = courseDirName + separator + "mergePdf";
        File titleDir = new File(courseDirName);
        if(!titleDir.exists()){
            titleDir.mkdir();
            new File(courseHtmlDirName).mkdir();
            new File(coursePdfDirName).mkdir();
            new File(courseMergePdfDirName).mkdir();
            logger.info("创建课程文件夹：{}, html目录：{} ,pdf目录：{}，合并pdf目录：{}"
                    , courseDirName, courseHtmlDirName, coursePdfDirName, courseMergePdfDirName);
        }
        List<String> coursePdfPaths = new ArrayList<>(1);
        GeekTimeArticles articles = new GeekTimeArticles(course.getId(), cookie);
        List<GeekTimeArticle> articleList = articles.getArticleList();
        if(articleList != null){
            logger.info("获取到文章列表：{}", articleList.size());
            for (GeekTimeArticle article : articleList) {
                String content = article.getContent();
                if(content != null){
                    String htmlPath = article2Html(article, courseHtmlDirName);
                    String pdf = html2Pdf(htmlPath, coursePdfDirName, article.getArticleTitle());
                    coursePdfPaths.add(pdf);
                }else {
                    logger.info("未获取到文章内容");
                }
                try {
                    Thread.sleep(1000L*3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else {
            logger.info("未获取到文章列表");
        }
        if(coursePdfPaths.size() > 0){
            mergePDF(courseMergePdfDirName, title, coursePdfPaths);
        }
        logger.info("获取课程：{}：结束。", title);

    }

    /**
     * 文章转换为html格式
     * @param article 文章信息
     * @param courseHtmlDirName html目录
     * @return html文章全路径
     * @throws IOException
     * @throws TemplateException
     */
    private String article2Html(GeekTimeArticle article, String courseHtmlDirName)
            throws IOException, TemplateException {
        Map<String, Object> map = new HashMap<>();
        String articleTitle = article.getArticleTitle();
        articleTitle = replace(articleTitle);
        String articlePath = courseHtmlDirName + separator + articleTitle + ".html";
        logger.info("转换文章为html格式开始：{}，路径：{}", articleTitle, articlePath);
        map.put("article", article);
        TemplateUtil.template2Html(init, GeekTimeConstant.template,
                articlePath, map);
        return articlePath;
    }

    /**
     * 转换html为pdf
     * @param articleHtmlPath html全路径
     * @param coursePdfDirName pdf目录
     * @param articleTitle 文章标题
     * @return pdf全路径
     * @throws IOException
     */
    private String html2Pdf(String articleHtmlPath, String coursePdfDirName, String articleTitle) throws IOException {
        articleTitle = replace(articleTitle);
        String pdfPath = coursePdfDirName + separator + articleTitle + ".pdf";
        logger.info("转换文章为pdf格式开始：{}，路径：{}", articleTitle, pdfPath);
        Itext7HtmlPdfUtil.html2Pdf(articleHtmlPath, pdfPath);
        return pdfPath;
    }

    /**
     *
     * @param courseMergePdfDirName 合并pdf目录
     * @param courseName 合并课程名称
     * @param pdfPaths 需要合并pdf路径
     * @return 合并pdf全路径
     * @throws IOException
     */
    private String mergePDF(String courseMergePdfDirName, String courseName, List<String> pdfPaths) throws IOException {
        courseName = replace(courseName);
        String mergePdfPath = courseMergePdfDirName + separator + courseName + ".pdf";
        logger.info("课程：{}，合并pdf开始：{}", courseName, mergePdfPath);
        PdfUtils.mergePdfByPath(mergePdfPath, pdfPaths);
        return mergePdfPath;
    }

    private String replace(String dirPath){
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
