package org.imzdong.geektime.ebook;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

public class KindleGenUtil {

    private static final String templateDir = getSourceDirPath();

    private static String getSourceDirPath() {
        URL resource = Objects.requireNonNull(EbookUtil.class.getClassLoader().getResource("bin"));
        try {
            // Convert the URL to a file path
            return Paths.get(resource.toURI()).toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get source directory path", e);
        }
    }

    private static final String KINDLEGEN_LINUX = templateDir+"/linux/kindlegen";
    private static final String KINDLEGEN_MAC = templateDir+"/mac/kindlegen";
    private static final String KINDLEGEN_WINDOWS = templateDir+"/windows/kindlegen.exe";

    public static String getKindlegenPath() {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("linux")) {
            return new File(KINDLEGEN_LINUX).getAbsolutePath();
        } else if (osName.contains("mac")) {
            return new File(KINDLEGEN_MAC).getAbsolutePath();
        } else if (osName.contains("windows")) {
            return new File(KINDLEGEN_WINDOWS).getAbsolutePath();
        } else {
            throw new RuntimeException("Unsupported system: " + osName);
        }
    }

    public static void main(String[] args) {
        System.out.println(getKindlegenPath());
    }

}
