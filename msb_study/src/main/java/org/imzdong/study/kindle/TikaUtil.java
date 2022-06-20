package org.imzdong.study.kindle;


import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.xml.sax.ContentHandler;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.langdetect.optimaize.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

/**
 * @author dong.zhou
 * @since 2022/6/20 10:59
 */
public class TikaUtil {

    public static void main(String[] args) throws Exception {
        String rootPath = "D:\\WorkSpace\\study\\dzs\\";
        String filename = rootPath + "123.docx";
        TikaConfig tikaConfig = TikaConfig.getDefaultConfig();

        Metadata metadata = new Metadata();
        String text = parseUsingComponents(filename, tikaConfig, metadata);
        System.out.println("Parsed Metadata: ");
        System.out.println(metadata);
        System.out.println("Parsed Text: ");
        System.out.println(text);

        System.out.println("-------------------------");

        metadata = new Metadata();
        text = parseUsingAutoDetect(filename, tikaConfig, metadata);
        System.out.println("Parsed Metadata: ");
        System.out.println(metadata);
        System.out.println("Parsed Text: ");
        System.out.println(text);
    }

    public static String parseUsingAutoDetect(String filename, TikaConfig tikaConfig,
        Metadata metadata) throws Exception {
        System.out.println("Handling using AutoDetectParser: [" + filename + "]");

        AutoDetectParser parser = new AutoDetectParser(tikaConfig);
        ContentHandler handler = new BodyContentHandler();
        TikaInputStream stream = TikaInputStream.get(new File(filename), metadata);
        parser.parse(stream, handler, metadata, new ParseContext());
        return handler.toString();
    }

    public static String parseUsingComponents(String filename, TikaConfig tikaConfig,
        Metadata metadata) throws Exception {
        MimeTypes mimeRegistry = tikaConfig.getMimeRepository();

        System.out.println("Examining: [" + filename + "]");

        metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, filename);
        System.out.println(
            "The MIME type (based on filename) is: [" + mimeRegistry.detect(null, metadata) +
                "]");

        InputStream stream = TikaInputStream.get(new File(filename));
        System.out.println(
            "The MIME type (based on MAGIC) is: [" + mimeRegistry.detect(stream, metadata) +
                "]");

        stream = TikaInputStream.get(new File(filename));
        Detector detector = tikaConfig.getDetector();
        System.out.println("The MIME type (based on the Detector interface) is: [" +
            detector.detect(stream, metadata) + "]");

        LanguageDetector langDetector = new OptimaizeLangDetector().loadModels();
        LanguageResult lang =
            langDetector.detect(FileUtils.readFileToString(new File(filename), UTF_8));

        System.out.println("The language of this content is: [" + lang.getLanguage() + "]");

        // Get a non-detecting parser that handles all the types it can
        Parser parser = tikaConfig.getParser();
        // Tell it what we think the content is
        MediaType type = detector.detect(stream, metadata);
        metadata.set(Metadata.CONTENT_TYPE, type.toString());
        // Have the file parsed to get the content and metadata
        ContentHandler handler = new BodyContentHandler();
        parser.parse(stream, handler, metadata, new ParseContext());

        return handler.toString();
    }
}
