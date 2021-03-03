package org.imzdong.tool.geektime;

import org.imzdong.tool.util.YamlUtil;

public class GeekTimeHandler {

    public static void main(String[] args) {
        String yamlPath = "geekTime.yaml";
        String cookie = YamlUtil.getYamlValueByProperty(yamlPath, "cookie");
        String articleId = "6458";
        GeekTimeArticle geekTimeArticle = new GeekTimeArticle(articleId, cookie);
        String articleContent = geekTimeArticle.getArticleContent();
        System.out.println(articleContent);
    }

}
