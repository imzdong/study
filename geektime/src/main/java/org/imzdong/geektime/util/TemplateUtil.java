package org.imzdong.geektime.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * @author zhoud
 * @since 2021/3/6 9:12
 */
public class TemplateUtil {

    /**
     * 初始化模板配置环境
     * @param templateDir 模板文件夹
     * @return
     */
    public static Configuration init(String templateDir){
        // Create your Configuration instance, and specify if up to what FreeMarker
        // version (here 2.3.29) do you want to apply the fixes that are not 100%
        // backward-compatible. See the Configuration JavaDoc for details.
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        // Specify the source where the template files come from. Here I set a
        // plain directory for it, but non-file-system sources are possible too:
        try {
            cfg.setDirectoryForTemplateLoading(new File(templateDir));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // From here we will set the settings recommended for new projects. These
        // aren't the defaults for backward compatibilty.
        // Set the preferred charset template files are stored in. UTF-8 is
        // a good choice in most applications:
        cfg.setDefaultEncoding("UTF-8");
        // Sets how errors will appear.
        // During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        // Don't log exceptions inside FreeMarker that it will thrown at you anyway:
        cfg.setLogTemplateExceptions(false);
        // Wrap unchecked exceptions thrown during template processing into TemplateException-s:
        cfg.setWrapUncheckedExceptions(true);
        // Do not fall back to higher scopes when reading a null loop variable:
        cfg.setFallbackOnNullLoopVariable(false);

        return cfg;
    }

    /**
     * 模板转换成html
     * @param cfg 模板容器
     * @param template 模板名称
     * @param htmlPath html地址
     * @param params 模板参数
     */
    public static void template2Html(Configuration cfg, String template,
                                     String htmlPath, Map<String, Object> params) throws IOException, TemplateException {
        /* Create a data-model */
        try (FileWriter fileWriter = new FileWriter(htmlPath)) {
            Template temp = cfg.getTemplate(template);
            /* Merge data-model with template */
            temp.process(params, fileWriter);
            // Note: Depending on what `out` is, you may need to call `out.close()`.
            // This is usually the case for file output, but not for servlet output.
        }
    }
}
