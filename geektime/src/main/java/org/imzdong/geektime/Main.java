package org.imzdong.geektime;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 启动类
 * @author zhoud
 * @since 2021/3/6 11:26
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws IOException, TemplateException {
        GeekTimeHandler geekTimeHandler = new GeekTimeHandler();
        geekTimeHandler.start();
        log.info("start");
    }
}
