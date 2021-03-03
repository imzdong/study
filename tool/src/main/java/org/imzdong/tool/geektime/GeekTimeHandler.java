package org.imzdong.tool.geektime;

import org.imzdong.tool.util.YamlUtil;

import java.util.ArrayList;
import java.util.List;

public class GeekTimeHandler {

    public static void main(String[] args) {
        String yamlPath = "geekTime.yaml";
        String cookie = YamlUtil.getYamlValueByProperty(yamlPath, "cookie");
        int prev = 0;
        GeekTimeCourses geekTimeCourses = new GeekTimeCourses(prev, 20, cookie);
        List<GeekTimeCourse> courses = geekTimeCourses.getCourses();
        List<GeekTimeCourse> list = new ArrayList<>();
        while (courses != null) {
            list.addAll(courses);
            geekTimeCourses.next();
            courses = geekTimeCourses.getCourses();
        }
        System.out.println(list);
        //getArticle(cookie);
    }

    private static void getArticle(String cookie) {
        String articleId = "6458";
        GeekTimeArticle geekTimeArticle = new GeekTimeArticle(articleId, cookie);
        String articleContent = geekTimeArticle.getArticleContent();
        System.out.println(articleContent);
    }

}
