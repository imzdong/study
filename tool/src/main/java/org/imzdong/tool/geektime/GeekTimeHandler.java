package org.imzdong.tool.geektime;

import org.imzdong.tool.util.YamlUtil;

import java.util.ArrayList;
import java.util.List;

public class GeekTimeHandler {

    public static void main(String[] args) {
        String yamlPath = "geekTime.yaml";
        String cookie = YamlUtil.getYamlValueByProperty(yamlPath, "cookie");
        //String articleCount = getData(cookie);
        //getCourses(cookie, Integer.parseInt(articleCount));
        //getArticle(cookie);
    }

    private static String getData(String cookie) {
        GeekTimeData geekTimeData = new GeekTimeData(cookie);
        return geekTimeData.getArticleCount();
    }

    private static List<GeekTimeCourse> getCourses(String cookie, int size) {
        GeekTimeCourses geekTimeCourses = new GeekTimeCourses(0, size, cookie);
        return geekTimeCourses.getCourses();
    }

    private static void getArticle(String cookie, String articleId) {
        articleId = "6458";
        GeekTimeArticle geekTimeArticle = new GeekTimeArticle(articleId, cookie);
        String articleContent = geekTimeArticle.getArticleContent();
        System.out.println(articleContent);
    }

}
