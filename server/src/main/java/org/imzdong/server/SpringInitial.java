package org.imzdong.server;

import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author admin
 * @since 2021/12/12 下午2:49
 */
public class SpringInitial extends AbstractContextLoaderInitializer {



    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }
}
