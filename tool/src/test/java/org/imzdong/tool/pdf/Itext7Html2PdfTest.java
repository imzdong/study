package org.imzdong.tool.pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.layout.font.FontProvider;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Itext7Html2PdfTest {

    private String outPath = "D:\\work\\pdf\\itext7\\";

    @Test
    public void html2Pdf(){
        String outPutFolder = "D:\\work\\pdf\\itext7\\";
        String orig = outPutFolder + "mb.html";
        File htmlSource = new File(orig);
        File pdfDest = new File(outPutFolder + "output.pdf");
        try {
            HtmlConverter.convertToPdf(new FileInputStream(htmlSource), new FileOutputStream(pdfDest));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void font2pdf(){
        FontProvider fontProvider = new DefaultFontProvider();
        for (String font : FONTS) {
            fontProvider.addFont(font);
        }
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setFontProvider(fontProvider);
        try {
            HtmlConverter.convertToPdf(new File(outPath + "mb.html"),
                    new File(outPath + "mb_ss_04.pdf"), converterProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * An array with the paths to extra fonts.
     */
    public static final String[] FONTS = {
            "font/NotoSans-Regular.ttf",
            "font/NotoSans-Bold.ttf",
            "font/NotoSansCJKsc-Regular.otf",
            "font/NotoSansCJKjp-Regular.otf",
            "font/NotoSansCJKkr-Regular.otf",
            "font/NotoNaskhArabic-Regular.ttf",
            "font/NotoSansHebrew-Regular.ttf"
    };
}
