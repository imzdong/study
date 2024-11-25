package org.imzdong.geektime.ebook;

import com.alibaba.fastjson.JSONObject;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class EbookUtil {

    private final Ebook ebook;
    private List<Heading> headings;
    private String templateDir = getSourceDirPath();

    private String getSourceDirPath() {
        URL resource = Objects.requireNonNull(EbookUtil.class.getClassLoader().getResource("templates"));
        try {
            // Convert the URL to a file path
            return Paths.get(resource.toURI()).toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get source directory path", e);
        }
    }

    public EbookUtil(Ebook ebook) throws IOException {
        this.ebook = ebook;
    }

    public List<Heading> getHeadings() {
        if (headings != null) {
            return headings;
        }
        headings = ebook.generateHeadings();
        return headings;
    }

    private void writeFile(String context, String toFilename) throws IOException {
        Writer out = new FileWriter(new File(ebook.getWorkFolder().toFile(), toFilename));
        out.write(context);
        out.close();
    }

    private void renderContainerXml() throws IOException {
        writeFile(FileUtil.generateContainContent(), "container.xml");
    }

    private void renderTocNcx() throws IOException {
        String ncxContent = FileUtil.generateNCXContent(ebook.getTitle(), ebook.getAuthor(), getHeadings());
        writeFile(ncxContent, "toc.ncx");
    }

    private void renderTocHtml() throws IOException {
        String tocFile = FileUtil.generateTOCContent(getHeadings());
        writeFile(tocFile, "toc.html");
    }

    private void renderOpf() throws IOException {
        writeFile(FileUtil.generateOPFContent(ebook.getTitle(),ebook.getAuthor(),
                getHeadings()), "content.opf");
    }

    private void saveCover() throws IOException {
        if (ebook.getCoverPath() != null) {
            Files.copy(ebook.getCoverPath(), ebook.getWorkFolder().resolve("cover.jpg"));
        } else {
            Files.copy(Paths.get(templateDir+"/cover.jpg"), ebook.getWorkFolder().resolve("cover.jpg"));
        }
    }

    private void moveSourceFiles() throws IOException {
        Files.walk(ebook.getSourceFolder())
                .filter(Files::isRegularFile)
                .forEach(source -> {
                    try {
                        Files.copy(source, ebook.getWorkFolder().resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void generateAllFiles() throws IOException, TemplateException {
        renderContainerXml();
        renderTocNcx();
        renderTocHtml();
        renderOpf();
        saveCover();
        moveSourceFiles();

        Path mimetypePath = ebook.getWorkFolder().resolve("mimetype");
        try (FileWriter writer = new FileWriter(mimetypePath.toFile())) {
            writer.write("application/epub+zip");
        }
    }

    private void createMobi(Path filePath) throws IOException, TemplateException, InterruptedException {
        generateAllFiles();
        String fn = filePath.getFileName().toString();
        String[] command = {KindleGenUtil.getKindlegenPath(), "-dont_append_source", ebook.getWorkFolder().resolve("content.opf").toString(), "-o", fn};
        log.info("command:{}", JSONObject.toJSONString(command));
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("KindleGen failed with exit code: " + exitCode);
        }
        Files.copy(ebook.getWorkFolder().resolve(fn), filePath);
    }

    private void createEpub(Path filePath) throws IOException, TemplateException {
        generateAllFiles();
        Path workFolder = ebook.getWorkFolder();
        try (FileOutputStream fos = new FileOutputStream(filePath.toFile());
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            zos.putNextEntry(new ZipEntry("mimetype"));
            zos.write("application/epub+zip".getBytes());
            zos.closeEntry();

            Files.walk(workFolder)
                    .filter(p -> !p.equals(workFolder))
                    .forEach(p -> {
                        try {
                            ZipEntry entry = new ZipEntry(workFolder.relativize(p).toString());
                            zos.putNextEntry(entry);
                            Files.copy(p, zos);
                            zos.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    public void create(Path filePath) throws IOException, TemplateException, InterruptedException {
        switch (ebook.getFormat()) {
            case "mobi":
                createMobi(filePath);
                break;
            case "epub":
                createEpub(filePath);
                break;
            default:
                throw new IllegalArgumentException("Unsupported format: " + ebook.getFormat());
        }
    }

}