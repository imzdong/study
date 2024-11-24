package org.imzdong.geektime.ebook;

import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Ebook {

    private final String title;
    private final String format;
    private final String author;
    private Path coverPath;
    private final List<Chapter> chapterList;
    private final Path sourceFolder;
    private final Path workFolder;
    private final EbookUtil ebookUtil;

    public Ebook(String title, Path sourceFolder, String author, String format) throws IOException {
        this.title = title;
        this.format = format;
        this.author = author;
        this.chapterList = new ArrayList<>();
        if (!Files.isDirectory(sourceFolder)) {
            throw new RuntimeException("Source folder not found: " + sourceFolder);
        }
        this.sourceFolder = sourceFolder;
        this.workFolder = sourceFolder.resolve("." + sanitizeFileName(title) + "." + RandomStringUtils.randomNumeric(3));
        try {
            Files.createDirectories(workFolder);
            Path imgsFolder = workFolder.resolve("imgs");
            Files.createDirectories(imgsFolder);
            Path sourceImgsFolder = sourceFolder.resolve("imgs");
            if (Files.exists(sourceImgsFolder)) {
                FileUtils.copyDirectory(sourceImgsFolder.toFile(), imgsFolder.toFile());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create work folder", e);
        }
        this.ebookUtil = new EbookUtil(this);
    }

    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[/\\\\?%*:\"'<>.,;=\\s+]", "");
    }

    public List<Map<String, Object>> generateHeadings() {
        List<Map<String, Object>> headings = new ArrayList<>();
        AtomicInteger order = new AtomicInteger(1);
        for (Chapter chapter : chapterList) {
            order.incrementAndGet();
            Map<String, Object> heading = new HashMap<>();
            heading.put("title", chapter.getTitle());
            heading.put("playOrder", order);
            heading.put("fileName", chapter.getFilePath().getFileName().toString());
            heading.put("subHeadings", chapter.getSubChapters().stream()
                    .map(subChapter -> {
                        order.incrementAndGet();
                        Map<String, Object> subHeading = new HashMap<>();
                        subHeading.put("title", subChapter.getTitle());
                        subHeading.put("playOrder", order.get());
                        subHeading.put("fileName", subChapter.getFilePath().getFileName().toString());
                        return subHeading;
                    })
                    .collect(Collectors.toList()));
            headings.add(heading);
        }
        return headings;
    }

    public Path getCoverPath() {
        return coverPath;
    }

    public Ebook setCover(Path coverPath) {
        if (coverPath == null || !Files.isRegularFile(coverPath)) {
            throw new IllegalArgumentException("Cover file not found: " + coverPath);
        }
        this.coverPath = coverPath;
        return this;
    }

    public List<Chapter> getChapterList() {
        return chapterList;
    }

    public Ebook addChapter(Chapter chapter) {
        if (!chapter.isTopChapter()) {
            throw new IllegalArgumentException("Only top-level chapters are accepted");
        }
        chapterList.add(chapter);
        return this;
    }

    public Chapter createChapter(String chapterTitle, Path chapterFilePath) {
        Chapter chapter = new Chapter(chapterTitle, this, chapterFilePath, 1);
        addChapter(chapter);
        return chapter;
    }

    public String getTitle() {
        return title;
    }

    public String getFormat() {
        return format;
    }

    public String getAuthor() {
        return author;
    }

    public Path getSourceFolder() {
        return sourceFolder;
    }

    public Path getWorkFolder() {
        return workFolder;
    }

    public void create(Path filePath) throws IOException, TemplateException, InterruptedException {
        if (chapterList.isEmpty()) {
            throw new IllegalArgumentException("Chapter list is empty");
        }
        ebookUtil.create(filePath);
    }

    public void saveTo(String filePath) throws IOException, TemplateException, InterruptedException {
        try {
            create(Paths.get(filePath));
        }catch (Exception e){
            System.out.println("try delete work folder:"+this.workFolder);
            Files.delete(this.workFolder);
            throw e;
        }
    }

    public void show() throws IOException, TemplateException, InterruptedException {
        Path tempFile = Paths.get(System.getProperty("user.dir"), "." + sanitizeFileName(title) + "." + format);
        create(tempFile);
        if (!Files.isRegularFile(tempFile)) {
            throw new FileNotFoundException("File not found: " + tempFile);
        }

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("linux")) {
            Runtime.getRuntime().exec("xdg-open " + tempFile.toString());
        } else if (os.contains("mac")) {
            Runtime.getRuntime().exec("open " + tempFile.toString());
        } else {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + tempFile.toString());
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (Files.exists(workFolder)) {
                FileUtils.deleteDirectory(workFolder.toFile());
            }
        } finally {
            super.finalize();
        }
    }
}