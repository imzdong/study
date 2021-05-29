package org.imzdong.tool.util;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.layout.font.FontProvider;

import java.io.File;
import java.io.IOException;

public class Itext7HtmlPdfUtil {

    /**
     * An array with the paths to extra fonts.
     */
    private static final String[] FONTS = {
            "font/NotoSans-Regular.ttf",
            "font/NotoSans-Bold.ttf",
            "font/NotoSansCJKsc-Regular.otf",
            "font/NotoSansCJKjp-Regular.otf",
            "font/NotoSansCJKkr-Regular.otf",
            "font/NotoNaskhArabic-Regular.ttf",
            "font/NotoSansHebrew-Regular.ttf"
    };

    /**
     * html转换pdf，支持中文转换
     * @param orgHtml 源html文件地址
     * @param destPdf 目标pdf
     */
    public static void html2Pdf(String orgHtml, String destPdf) throws IOException {
        FontProvider fontProvider = new DefaultFontProvider();
        for (String font : FONTS) {
            fontProvider.addFont(font);
        }
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setFontProvider(fontProvider);
        HtmlConverter.convertToPdf(new File(orgHtml),
                new File(destPdf), converterProperties);
    }
}
