package org.imzdong.server;

import org.imzdong.server.config.SpringConfig;
import org.imzdong.server.serlevt.HelloWorldServlet;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author admin
 * @since 2021/12/12 下午2:49
 */
public class SpringInitial extends AbstractContextLoaderInitializer {


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.addServlet("myServlet", HelloWorldServlet.class);
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringConfig.class);
        context.refresh();
        return context;
    }
}
