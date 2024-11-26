
package org.imzdong.geektime.ebook;

import freemarker.template.TemplateException;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class EbookMaker {

    private static String getSourceDirPath() {
        URL resource = Objects.requireNonNull(EbookMaker.class.getClassLoader().getResource("examples/source"));
        try {
            // Convert the URL to a file path
            return Paths.get(resource.toURI()).toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get source directory path", e);
        }
    }

    public static void main(String[] args) {

        String sourceDir = getSourceDirPath();
        String outputDir = "D:\\Downloads\\geektime\\test";

        String author = "Winter";

        //makeEbook(sourceDir, outputDir, author,"epub");

        makeEbook(sourceDir, outputDir, author, "mobi");
    }

    public static void makeEbook(String sourceDir, String outputDir, String author, String format) {
        try {
            String tocFileName = Paths.get(sourceDir, "toc.md").toString();
            Toc toc = parseHeaders(tocFileName);

            String title = toc.getTitle();
            Ebook ebook = new Ebook(title, Paths.get(sourceDir), author, format);
            Path coverPath = Paths.get(sourceDir, "cover.jpg");
            if (Files.exists(coverPath)) {
                ebook.setCover(coverPath);
            }

            LinkedHashMap<String, List<String>> firstTitle = toc.getSubTitle();
            firstTitle.forEach((chapterTitle,subChapters) -> {
                Path chapterFilePath = Paths.get(sourceDir, chapterTitle + ".html");
                Chapter chapter = ebook.createChapter(chapterTitle, chapterFilePath);
                for (String subchapterTitle : subChapters) {
                    Path subchapterFilePath = Paths.get(sourceDir, subchapterTitle + ".html");
                    chapter.createSubChapter(subchapterTitle, subchapterFilePath);
                }
            });

            String fileName = Paths.get(outputDir, title + "." + format).toAbsolutePath().toString();
            ebook.saveTo(fileName);
            System.out.println("Success");
        } catch (IOException | IllegalArgumentException | TemplateException | InterruptedException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static Toc parseHeaders(String tocFileName) throws IOException {
        Path path = Paths.get(tocFileName);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("toc.md file not found: " + tocFileName);
        }

        Toc toc = new Toc();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String[] lines = reader.lines().toArray(String[]::new);
            if (lines.length == 0) {
                throw new IllegalArgumentException("Invalid toc.md file: title is empty");
            }

            int titleLine = 0;
            String title = lines[titleLine].trim();
            if (title.isEmpty()) {
                throw new IllegalArgumentException("Invalid toc.md file: title is empty");
            }
            toc.setTitle(title);
            LinkedHashMap<String, List<String>> headersInfo = new LinkedHashMap<>();
            String fTitle = null;
            for (int i = titleLine + 1; i < lines.length; i++) {
                String line = lines[i].trim();
                if (line.startsWith("# ")) {
                    line = line.substring(2);
                    fTitle = line;
                    headersInfo.putIfAbsent(line, new ArrayList<>());
                } else if (line.startsWith("## ")) {
                    line = line.substring(3);
                    List<String> subTitles = headersInfo.putIfAbsent(fTitle, new ArrayList<>());
                    subTitles.add(line);
                }
            }
            if (headersInfo.isEmpty()) {
                throw new IllegalArgumentException("Invalid toc.md file: headings are empty");
            }
            toc.setSubTitle(headersInfo);
        }

        return toc;
    }
}