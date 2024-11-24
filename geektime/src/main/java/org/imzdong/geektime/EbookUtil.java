package org.imzdong.geektime;

import org.imzdong.geektime.ebook.Toc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class EbookUtil {

    public static void main(String[] args) {
        try {
            // 创建数据模型
            Toc toc = new Toc();
            toc.setTitle("test");

            LinkedHashMap<String, List<String>> subTitles = new LinkedHashMap<>();
            subTitles.put("tt1", List.of("1","2","3","4","5","6","7","8","9"));
            subTitles.put("tt2", List.of("1","2","3","4","5","8","9"));
            subTitles.put("tt3", List.of("1","6","7","8","9"));

            toc.setFirstTitle(subTitles);
            // 生成 Markdown 文件
            generateMarkdownFile("D:\\Downloads\\geektime\\output1.md", toc);

            System.out.println("Markdown file generated successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateMarkdownFile(String filePath, Toc toc) throws IOException {
        try (FileWriter writer = new FileWriter(new File(filePath))) {
            // 写入标题
            writer.write(toc.getTitle() + "\n\n");

            LinkedHashMap<String, List<String>> firstTitle = toc.getFirstTitle();
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
