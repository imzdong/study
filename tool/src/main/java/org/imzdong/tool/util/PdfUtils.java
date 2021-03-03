package org.imzdong.tool.util;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public static void main(String[] args) throws Exception{
        String result = "D:/work/pdf/result.pdf";
        File resultFile = new File(result);
        String [] mergePdfs = new String[]{"D:\\work\\pdf\\before\\1.pdf","D:\\work\\pdf\\before\\2.pdf"};
        File[] resultFiles = new File[2];
        for (int i = 0; i < mergePdfs.length; i++) {
            resultFiles[i] = new File(mergePdfs[i]);
        }
        mergePDF(resultFile, resultFiles);
    }

}
