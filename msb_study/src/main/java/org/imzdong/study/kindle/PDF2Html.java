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
import org.fit.pdfdom.PDFToHTML;
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
        String rootPath = "D:\\WorkSpace\\study\\dzs\\";
        String pdfFileName = "table.pdf";
        String infile = rootPath + pdfFileName;
        String outfileDom = rootPath + "table-pdfDom.html";
        String outfileTika = rootPath + "table-pdfTika.html";
        tikaPdf2Html(infile, outfileTika);
        String[] pp = {infile, outfileDom};
        pdfDomPdf2Html(pp);
    }

    public static void tikaPdf2Html(String infile, String outfile) throws Exception{
        //PDDocument pdDocument = PDDocument.load(new File(rootPath+pdfFileName));
        ContentHandler handler = new ToHTMLContentHandler(new FileOutputStream(outfile), "UTF8");
        ParseContext pcontext = new ParseContext();
        Metadata metadata = new Metadata();
        PDFParser pdfparser = new PDFParser();
        pdfparser.parse(new FileInputStream(infile), handler, metadata, pcontext);
        /*PDFParserConfig pdfParserConfig = new PDFParserConfig();
        PDFMarkedContent2XHTML.process(pdDocument, handler, pcontext, metadata, pdfParserConfig)*/;
        String[] metadataNames = metadata.names();
        for(String name : metadataNames) {
            System.out.println(name+ " : " + metadata.get(name));
        }
    }

    public static void pdfDomPdf2Html(String args []) throws Exception{
        PDFToHTML.main(args);
    }
}
