package org.imzdong.geektime.ebook;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Chapter {

    private static final int MAX_LEVEL = 2;
    private final String title;
    private final Ebook ebook;
    private final Path filePath;
    private final int level;
    private final List<Chapter> subChapters;

    public Chapter(String title, Ebook ebook, Path filePath, int level) {
        this.title = title;
        this.ebook = ebook;
        this.filePath = filePath;
        this.level = level;
        this.subChapters = new ArrayList<>();
    }

    public boolean isTopChapter() {
        return level == 1;
    }

    public Chapter createSubChapter(String chapterTitle, Path chapterFilePath) {
        if (level >= MAX_LEVEL) {
            throw new IllegalArgumentException("Chapter level exceeds maximum level of " + MAX_LEVEL);
        }
        Chapter subChapter = new Chapter(chapterTitle, ebook, chapterFilePath, level + 1);
        subChapters.add(subChapter);
        return subChapter;
    }

    public String getTitle() {
        return title;
    }

    public Path getFilePath() {
        return filePath;
    }

    public List<Chapter> getSubChapters() {
        return subChapters;
    }
}