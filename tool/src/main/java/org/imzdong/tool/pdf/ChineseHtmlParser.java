package org.imzdong.tool.pdf;

import com.lowagie.text.DocListener;
import com.lowagie.text.html.HtmlParser;
import com.lowagie.text.html.SAXmyHtmlHandler;
import com.lowagie.text.pdf.BaseFont;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.Reader;

public class ChineseHtmlParser extends HtmlParser {

    public ChineseHtmlParser() {
        super();
    }

    public void parseGbk(DocListener document, InputStream is) {
        try {
            BaseFont bfComic = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            parser.parse(is, new SAXmyHtmlHandler(document, bfComic));
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

}
