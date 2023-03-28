package org.imzdong.geektime;

import com.lowagie.text.DocumentException;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.imzdong.geektime.util.Itext7HtmlPdfUtil;
import org.imzdong.geektime.util.PdfUtils;
import org.imzdong.geektime.util.TemplateUtil;
import org.imzdong.geektime.util.YamlUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 极客时间获取课程信息启动类
 * 仅仅修改yml配置类
 * @author zhoud
 * @date 2021-03-06
 */
@Slf4j
public class GeekTimeHandler {

    private String separator = File.separator;
    private String account;
    private String password;
    private String outPath;
    private String courseName;
    private String mergePdf;

    public GeekTimeHandler(){
        init();
    }

    private void init() {
        String yamlPath = "geekTime.yaml";
        Map<String, Object> yamlProperties = YamlUtil.getYamlProperties(yamlPath);
        account = yamlProperties.get("account").toString();
        password = yamlProperties.get("password").toString();
        outPath = yamlProperties.get("outPath").toString();
        courseName = yamlProperties.get("courseName").toString();
        mergePdf = yamlProperties.get("mergePdf").toString();
    }

    /**
     * 启动任务开始
     * @throws IOException
     * @throws TemplateException
     */
    public void start() throws IOException, TemplateException {
        String fileName = Objects.requireNonNull(this.getClass().getClassLoader().getResource(GeekTimeConstant.templateDir)).getPath();
        Configuration cfg = TemplateUtil.init(fileName);
        if(cfg == null){
            throw new RuntimeException("初始化失败");
        }
        GeekTimeLogin geekTimeLogin = new GeekTimeLogin(account, password);
        boolean login = geekTimeLogin.login();
        if(!login){
            throw new RuntimeException("login fail");
        }
        GeekTimeData geekTimeData = new GeekTimeData();
        Integer courseCount = geekTimeData.getCourseCount();
        log.info("获取课程总数：{}", courseCount);
        if(courseCount > 0){
            GeekTimeCourses geekTimeCourses = new GeekTimeCourses(0, courseCount);
            List<GeekTimeCourse> courses = null;
            if(courseName != null){
                courses = geekTimeCourses.getCoursesByName(courseName);
            }else {
                courses = geekTimeCourses.getCourses();
            }
            if(courses != null){
                log.info("获取到课程信息：{}", courses.size());
                for (GeekTimeCourse course : courses) {
                    String title = course.getTitle();
                    log.info("获取课程：{}：开始。", title);
                    String courseDirName = outPath + separator + title;
                    String courseHtmlDirName = courseDirName + separator + "html";
                    String coursePdfDirName = courseDirName + separator + "pdf";
                    String courseMergePdfDirName = courseDirName + separator + "mergePdf";
                    initCourseDir(courseDirName, courseHtmlDirName, coursePdfDirName, courseMergePdfDirName);
                    List<String> coursePdfPaths = new ArrayList<>(courses.size());

                    listArticlesOfCourse(cfg, course, courseHtmlDirName, coursePdfDirName, coursePdfPaths);
                    if(Objects.equals(mergePdf, "true") && coursePdfPaths.size() > 0){
                        mergePDF(courseMergePdfDirName, title, coursePdfPaths);
                    }
                    log.info("获取课程：{}：结束。", title);
                }
            }else {
                log.info("未获取到课程信息");
            }
        }
    }

    private void listArticlesOfCourse(Configuration cfg, GeekTimeCourse course, String courseHtmlDirName, String coursePdfDirName,
        List<String> coursePdfPaths) throws IOException, TemplateException {
        GeekTimeArticles articles = new GeekTimeArticles(course.getId());
        List<GeekTimeArticle> articleList = articles.getArticleList();
        if(articleList != null){
            log.info("获取到文章列表：{}", articleList.size());
            for (GeekTimeArticle article : articleList) {
                String content = article.getContent();
                if(content != null){
                    String htmlPath = article2Html(article, courseHtmlDirName, cfg);
                    String pdf = html2Pdf(htmlPath, coursePdfDirName, article.getArticleTitle());
                    coursePdfPaths.add(pdf);
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

    private void initCourseDir(String courseDirName, String courseHtmlDirName, String coursePdfDirName,
        String courseMergePdfDirName) {
        File titleDir = new File(courseDirName);
        if(!titleDir.exists()){
            new File(courseHtmlDirName).mkdirs();
            new File(coursePdfDirName).mkdirs();
            new File(courseMergePdfDirName).mkdirs();
            log.info("创建课程文件夹：{}, html目录：{} ,pdf目录：{}，合并pdf目录：{}"
                    , courseDirName, courseHtmlDirName, coursePdfDirName, courseMergePdfDirName);
        }
    }

    /**
     * 文章转换为html格式
     * @param article 文章信息
     * @param courseHtmlDirName html目录
     * @return html文章全路径
     * @throws IOException
     * @throws TemplateException
     */
    private String article2Html(GeekTimeArticle article, String courseHtmlDirName, Configuration cfg)
            throws IOException, TemplateException {
        Map<String, Object> map = new HashMap<>();
        String articleTitle = article.getArticleTitle();
        articleTitle = replace(articleTitle);
        String articlePath = courseHtmlDirName + separator + articleTitle + ".html";
        log.info("转换文章为html格式开始：{}，路径：{}", articleTitle, articlePath);
        map.put("article", article);
        TemplateUtil.template2Html(cfg, GeekTimeConstant.template, articlePath, map);
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
        log.info("转换文章为pdf格式开始：{}，路径：{}", articleTitle, pdfPath);
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
        log.info("课程：{}，合并pdf开始：{}", courseName, mergePdfPath);
        try {
            PdfUtils.mergePdfByPath(mergePdfPath, pdfPaths);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
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
