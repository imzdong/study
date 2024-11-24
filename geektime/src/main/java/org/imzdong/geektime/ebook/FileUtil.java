package org.imzdong.geektime.ebook;

import java.util.List;

public class FileUtil {

    public static String generateContainContent() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>\n" +
                "<container xmlns=\"urn:oasis:names:tc:opendocument:xmlns:container\" version=\"1.0\">\n" +
                "    <rootfiles>\n" +
                "        <rootfile full-path=\"content.opf\" media-type=\"application/oebps-package+xml\"/>\n" +
                "    </rootfiles>\n" +
                "</container>";
    }

    public static String generateNCXContent(String title, String author, List<Heading> headings) {
        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<!DOCTYPE ncx PUBLIC \"-//NISO//DTD ncx 2005-1//EN\" \"http://www.daisy.org/z3986/2005/ncx-2005-1.dtd\">\n");
        sb.append("<ncx xmlns=\"http://www.daisy.org/z3986/2005/ncx/\" version=\"2005-1\" xml:lang=\"en-US\">\n");
        sb.append("    <head>\n");
        sb.append("        <meta name=\"dtb:uid\" content=\"BookId\"/>\n");
        sb.append("        <meta name=\"dtb:depth\" content=\"2\"/>\n");
        sb.append("        <meta name=\"dtb:totalPageCount\" content=\"0\"/>\n");
        sb.append("        <meta name=\"dtb:maxPageNumber\" content=\"0\"/>\n");
        sb.append("    </head>\n");
        sb.append("    <docTitle><text>").append(title).append("</text></docTitle>\n");
        sb.append("    <docAuthor><text>").append(author).append("</text></docAuthor>\n");
        sb.append("    <navMap>\n");
        sb.append("        <navPoint class=\"toc\" id=\"toc\" playOrder=\"1\">\n");
        sb.append("            <navLabel><text>Table of Contents</text></navLabel>\n");
        sb.append("            <content src=\"toc.html\"/>\n");
        sb.append("        </navPoint>\n");

        for (int i = 0; i < headings.size(); i++) {
            Heading heading = headings.get(i);
            sb.append("        <navPoint class=\"chapter\" id=\"chapter_").append(i + 1).append("\" playOrder=\"").append(heading.getPlayOrder()).append("\">\n");
            sb.append("            <navLabel><text>").append(heading.getTitle()).append("</text></navLabel>\n");
            sb.append("            <content src=\"").append(heading.getFileName()).append("\"/>\n");

            for (int j = 0; j < heading.getSubHeadings().size(); j++) {
                Heading subHeading = heading.getSubHeadings().get(j);
                sb.append("            <navPoint class=\"section\" id=\"section_").append(i + 1).append(".").append(j + 1).append("\" playOrder=\"").append(subHeading.getPlayOrder()).append("\">\n");
                sb.append("                <navLabel><text>").append(subHeading.getTitle()).append("</text></navLabel>\n");
                sb.append("                <content src=\"").append(subHeading.getFileName()).append("\"/>\n");
                sb.append("            </navPoint>\n");
            }

            sb.append("        </navPoint>\n");
        }

        sb.append("    </navMap>\n");
        sb.append("</ncx>");

        return sb.toString();
    }

    public static String generateTOCContent(List<Heading> headings) {
        StringBuilder sb = new StringBuilder();

        // 开始生成 HTML 内容
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n");
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
        sb.append("<head>\n");
        sb.append("    <title>Table of Contents</title>\n");
        sb.append("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        sb.append("    <h1><b>TABLE OF CONTENTS</b></h1>\n");
        sb.append("    <br/>\n");

        for (Heading h : headings) {
            sb.append("    <h3><b><a href=\"").append(h.getFileName()).append("\">").append(h.getTitle()).append("</a></b></h3>\n");
            sb.append("    <ul>\n");
            for (Heading sh : h.getSubHeadings()) {
                sb.append("        <li><a href=\"").append(sh.getFileName()).append("\">").append(sh.getTitle()).append("</a></li>\n");
            }
            sb.append("    </ul>\n");
        }

        sb.append("</body>\n");
        sb.append("</html>\n");

        return sb.toString();
    }

    public static String generateOPFContent(String title, String author, List<Heading> headings) {
        StringBuilder sb = new StringBuilder();

        // 开始生成 OPF 内容
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<package xmlns=\"http://www.idpf.org/2007/opf\" version=\"2.0\" unique-identifier=\"BookId\">\n");

        // 生成 metadata 部分
        sb.append("    <metadata xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n");
        sb.append("        <dc:title>").append(title).append("</dc:title>\n");
        sb.append("        <dc:language>zh-cn</dc:language>\n");
        sb.append("        <dc:creator>").append(author).append("</dc:creator>\n");
        sb.append("        <meta name=\"cover\" content=\"cover_image\"/>\n");
        sb.append("    </metadata>\n");

        // 生成 manifest 部分
        sb.append("    <manifest>\n");
        sb.append("        <item id=\"cover_image\" href=\"cover.jpg\" media-type=\"image/jpeg\"/>\n");
        sb.append("        <item id=\"toc\" media-type=\"application/x-dtbncx+xml\" href=\"toc.ncx\"/>\n");
        sb.append("        <item id=\"toc_html\" media-type=\"application/xhtml+xml\" href=\"toc.html\"/>\n");

        int chapterIndex = 1;
        for (Heading h : headings) {
            sb.append("        <item id=\"chapter_").append(chapterIndex).append("\" media-type=\"application/xhtml+xml\" href=\"").append(h.getFileName()).append("\"/>\n");
            int sectionIndex = 1;
            for (Heading sh : h.getSubHeadings()) {
                sb.append("        <item id=\"session_").append(chapterIndex).append(".").append(sectionIndex).append("\" media-type=\"application/xhtml+xml\" href=\"").append(sh.getFileName()).append("\"/>\n");
                sectionIndex++;
            }
            chapterIndex++;
        }

        sb.append("    </manifest>\n");

        // 生成 spine 部分
        sb.append("    <spine toc=\"toc\">\n");
        sb.append("        <itemref idref=\"toc_html\"/>\n");

        chapterIndex = 1;
        for (Heading h : headings) {
            sb.append("        <itemref idref=\"chapter_").append(chapterIndex).append("\"/>\n");
            int sectionIndex = 1;
            for (Heading sh : h.getSubHeadings()) {
                sb.append("        <itemref idref=\"session_").append(chapterIndex).append(".").append(sectionIndex).append("\"/>\n");
                sectionIndex++;
            }
            chapterIndex++;
        }

        sb.append("    </spine>\n");
        sb.append("</package>\n");

        // 将生成的 OPF 内容写入文件
        return sb.toString();
    }

}
