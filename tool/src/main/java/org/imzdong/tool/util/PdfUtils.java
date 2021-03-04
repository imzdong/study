package org.imzdong.tool.util;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import org.imzdong.tool.pdf.ChineseHtmlParser;

import java.io.*;

public class PdfUtils {

    /**
     * 合并多个 PDF 文件
     *
     * @param mergedPdf 合并后的文件
     * @param pdfs      待合并的文件
     * @throws DocumentException 读取 PDF 文件异常
     * @throws IOException       合并异常
     */
    public static void mergePDF(File mergedPdf, File... pdfs) throws DocumentException, IOException {
        Document doc = new Document();
        PdfCopy copy = new PdfCopy(doc, new FileOutputStream(mergedPdf));
        doc.open();
        for (File pdf : pdfs) {
            PdfReader pdfreader = new PdfReader(new FileInputStream(pdf));
            int n = pdfreader.getNumberOfPages();
            for (int i = 1; i <= n; i++) {
                copy.addPage(copy.getImportedPage(pdfreader, i));
            }
            copy.freeReader(pdfreader);
        }
        doc.close();
        copy.close();
    }

    /**
     * html转换pdf
     * @param htmlText html文件内容
     * @param pdf pdf文件地址
     */
    public static void html2Pdf(String htmlText, String pdf) {
        // step 1: creation of a document-object
        try (Document document = new Document()) {
            PdfWriter.getInstance(document, new FileOutputStream(pdf));
            // step 2: we open the document
            document.open();
            // step 3: parsing the HTML document to convert it in PDF
            ChineseHtmlParser chineseHtmlParser = new ChineseHtmlParser();
            byte[] bytes = htmlText.getBytes();
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            chineseHtmlParser.parseGbk(document, stream);
        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }
    }

}
