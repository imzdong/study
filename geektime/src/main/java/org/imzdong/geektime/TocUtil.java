package org.imzdong.geektime;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.imzdong.geektime.ebook.Toc;
import org.imzdong.geektime.model.CourseChapter;
import org.imzdong.geektime.model.CourseInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class TocUtil {

    public static void main(String[] args) {
        //testToc();
        changeToc();

        String directoryPath = "D:\\Download\\geektime\\test\\"; // 目录路径

        changFileName(directoryPath);
    }

    private static void changFileName(String directoryPath) {
        try {
            // 获取目录下的所有文件
            Path dir = Paths.get(directoryPath);
            if (!Files.isDirectory(dir)) {
                System.out.println("指定的路径不是一个目录！");
                return;
            }

            // 遍历目录下的所有文件
            try (Stream<Path> stream = Files.walk(dir)) {
                List<Path> files = stream.filter(Files::isRegularFile).collect(Collectors.toList());

                for (Path file : files) {
                    String originalFileName = file.getFileName().toString();
                    if(originalFileName.equals("toc.md") || originalFileName.equals("cover.jpg")){
                        continue;
                    }
                    String noName = "";
                    noName = originalFileName.replaceAll("\\.html", "");
                    String newFileName = escapeFileName(noName)+".html";

                    if (!originalFileName.equals(newFileName)) {
                        Path newFilePath = file.getParent().resolve(newFileName);
                        Files.move(file, newFilePath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("文件名已从 " + originalFileName + " 更改为 " + newFileName);
                    }
                }
            }

            System.out.println("文件名替换完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void changeToc() {
        String filePath = "D:\\Download\\geektime\\test\\toc.md"; // 文件路径

        Path path = Paths.get(filePath);
        try {
            // 读取文件的所有行
            List<String> lines = Files.readAllLines(path);

            // 替换每一行中的特殊字符
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                String[] s = line.split(" ");
                String prf = s[0];
                String updatedLine = escapeFileName(line.replaceAll(prf, ""));
                updatedLines.add(prf+" "+updatedLine);
            }

            // 将更新后的内容写回文件
            Files.write(path, updatedLines, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("文件已成功更新！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testToc() {
        try {
            // 创建数据模型
            Toc toc = new Toc();
            toc.setTitle("test");

            LinkedHashMap<String, List<String>> subTitles = new LinkedHashMap<>();
            subTitles.put("tt1", List.of("1","2","3","4","5","6","7","8","9"));
            subTitles.put("tt2", List.of("1","2","3","4","5","8","9"));
            subTitles.put("tt3", List.of("1","6","7","8","9"));

            toc.setSubTitle(subTitles);
            // 生成 Markdown 文件
            generateMarkdownFile("D:\\Downloads\\geektime\\output1.md", toc);

            System.out.println("Markdown file generated successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Toc fillToc(CourseInfo info, List<CourseChapter> chapters,
                              List<String> articles, String courseHtmlPath){
        log.info("articles:{}", JSONObject.toJSONString(articles));
        Toc toc = new Toc();
        toc.setTitle(info.getColumnTitle());
        LinkedHashMap<String, List<String>> subTitle = new LinkedHashMap<>();
        int start = 0;//1,3,4,8,10,5,2
        for (CourseChapter chapter : chapters) {
            String title = escapeFileName(chapter.getTitle());
            createEmptyHtml(courseHtmlPath, title);
            Integer articleCount = chapter.getArticleCount();
            List<String> subbed = articles.subList(start, start+articleCount);
            start = start + articleCount;
            subTitle.put(title, subbed);
        }
        toc.setSubTitle(subTitle);
        return toc;
    }

    /**
     * 转义文件名中的特殊字符
     *
     * @param fileName 原始文件名
     * @return 转义后的文件名
     */
    public static String escapeFileName(String fileName) {
        if (fileName == null) {
            return null;
        }
        // 使用正则表达式移除非法字符
        return fileName.replaceAll("[/\\\\?%*:\"'<>,.;=？\\s+]+", "");

    }

    private static void createEmptyHtml(String courseHtmlPath, String fileName){
        String content = "<body></body>";
        try (FileWriter writer = new FileWriter((courseHtmlPath + File.separator+ fileName+".html"))) {
            writer.write(content);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void generateMarkdownFile(String filePath, Toc toc) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            // 写入标题
            writer.write(toc.getTitle() + "\n\n");

            LinkedHashMap<String, List<String>> firstTitle = toc.getSubTitle();
            Set<Map.Entry<String, List<String>>> entries = firstTitle.entrySet();
            for (Map.Entry<String, List<String>> entry : entries) {
                String title = entry.getKey();
                List<String> items = entry.getValue();
                // 写入列表
                writer.write("# "+title+"\n");
                for (String item : items) {
                    writer.write("## " + item + "\n");
                }
                writer.write("\n");
            }
            // 写入段落
            writer.write("\n");

        }
    }

}
