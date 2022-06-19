package org.imzdong.study.kindle;

import pdf.converter.PdfConverter;

import java.io.File;

/**
 * @author zhoud
 * @since 2022/6/19 19:30
 */
public class EpubUtil {

    public static void main(String[] args) {
        String pdfPath = "D:\\Downloads\\kindle-1.2.4\\zdy-dzs\\jv";
        PdfConverter.convert(new File(pdfPath + "\\old\\lsxyb.pdf"))
                .intoEpub("Moby Dick", new File(pdfPath +"\\out\\ll.epub"));
    }
}
