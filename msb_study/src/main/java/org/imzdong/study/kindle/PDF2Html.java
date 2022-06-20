package org.imzdong.study.kindle;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFMarkedContent2XHTML;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.xml.sax.ContentHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author dong.zhou
 * @since 2022/6/20 13:26
 */
public class PDF2Html {

    public static void main(String[] args) throws Exception {
        tikaPdf2XHtml();
    }

    public static void itextPdf2Html() throws Exception{
        // IO
        File htmlSource = new File("input.html");
        File pdfDest = new File("output.pdf");
        // pdfHTML specific code
        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(new FileInputStream(htmlSource),
            new FileOutputStream(pdfDest), converterProperties);
    }

    public static void tikaPdf2Html() throws Exception{
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(new File("d:/唐诗三百首.pdf"));

        ParseContext pcontext = new ParseContext();

        //parsing the document using PDF parser
        PDFParser pdfparser = new PDFParser();
        pdfparser.parse(inputstream, handler, metadata,pcontext);
    }

    public static void tikaPdf2XHtml() throws Exception{
        String rootPath = "D:\\WorkSpace\\study\\dzs\\";
        String pdfFileName = "table.pdf";
        //PDDocument pdDocument = PDDocument.load(new File(rootPath+pdfFileName));
        String outHtml = "table.html";
        ContentHandler handler = new ToHTMLContentHandler(new FileOutputStream(rootPath+outHtml), "UTF8");
        ParseContext pcontext = new ParseContext();
        Metadata metadata = new Metadata();
        PDFParser pdfparser = new PDFParser();
        pdfparser.parse(new FileInputStream(rootPath+pdfFileName), handler, metadata, pcontext);
        /*PDFParserConfig pdfParserConfig = new PDFParserConfig();
        PDFMarkedContent2XHTML.process(pdDocument, handler, pcontext, metadata, pdfParserConfig)*/;
        String[] metadataNames = metadata.names();
        for(String name : metadataNames) {
            System.out.println(name+ " : " + metadata.get(name));
        }
    }
}
