package org.imzdong.tool.ddd;

import com.itextpdf.html2pdf.HtmlConverter;
import com.overzealous.remark.Remark;
import org.apache.commons.io.FileUtils;
import org.imzdong.tool.util.Itext7HtmlPdfUtil;
import org.imzdong.tool.util.YamlUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DddMain {

    public static void main(String[] args) throws Exception{
        String html1 = "https://developer.aliyun.com/article/716908";
        String html2 = "https://developer.aliyun.com/article/719251";
        String html3 = "https://developer.aliyun.com/article/758292";
        String html4 = "https://developer.aliyun.com/article/784117";
        String html5 = "https://developer.aliyun.com/article/783885";
        Map<String, String> urlMap = new HashMap<>();
        urlMap.put("1.详解DDD", html1);
        urlMap.put("2.详解DDD", html2);
        urlMap.put("3.详解DDD", html3);
        urlMap.put("4.详解DDD", html4);
        urlMap.put("5.详解DDD", html5);
        String yamlPath = "geekTime.yaml";
        Map<String, String> yamlProperties = YamlUtil.getYamlProperties(yamlPath);
        String outPath = yamlProperties.get("outPath") + File.separator + "DDD";

        for (Map.Entry<String, String> entry : urlMap.entrySet()) {
            URL url = new URL(entry.getValue());
            Document htmlDoc = Jsoup.parse(url, 5000);
            String title = htmlDoc.title();
            Remark remark = new Remark();
            String html = remark.convert(url, 5000);
            //creat pdf
            FileUtils.writeStringToFile(new File(outPath + File.separator + title+".md"),
                    html, "utf-8", false);
            //create pdf
            Itext7HtmlPdfUtil.orgHtml2Pdf(htmlDoc.body().html(), outPath + File.separator + title+"-body.pdf");
            Itext7HtmlPdfUtil.orgHtml2Pdf(htmlDoc.html(), outPath + File.separator + title+".pdf");
        }
    }
}
