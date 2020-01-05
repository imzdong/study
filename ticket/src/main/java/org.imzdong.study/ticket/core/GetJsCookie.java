package org.imzdong.study.ticket.core;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/5
 */
public class GetJsCookie {
    private Logger logger = LoggerFactory.getLogger(GetJsCookie.class);


    private String cookieUrl = null;

    public String getCookieUrl(String ip, String port) {
        WebClient wc = null;
        try {
            if (StringUtils.isNotBlank(ip)) {
                wc = new WebClient(BrowserVersion.CHROME, ip, Integer.parseInt(port));
            } else {
                wc = new WebClient();
            }
//           如果代理需要密码
//            DefaultCredentialsProvider creds = new DefaultCredentialsProvider();
//            creds.addCredentials("username", "password");
//            wc.setCredentialsProvider(creds);
            wc.getOptions().setTimeout(15000);
            wc.getOptions().setUseInsecureSSL(true);
            wc.getOptions().setJavaScriptEnabled(true);
            wc.getOptions().setCssEnabled(false);
            //当JS执行出错的时候是否抛出异常, 这里选择不需要
            wc.getOptions().setThrowExceptionOnScriptError(true);
            //当HTTP的状态非200时是否抛出异常
            wc.getOptions().setThrowExceptionOnFailingStatusCode(true);
            //很重要，设置支持AJAX
            wc.setAjaxController(new AjaxController() {
                private static final long serialVersionUID = -7255795662011210223L;
                @Override
                public boolean processSynchron(HtmlPage page, WebRequest settings, boolean async) {
                    //					logger.info("AjaxController " + settings.getUrl().toString());
                    return super.processSynchron(page, settings, async);
                }
            });
            wc.setWebConnection(
                    new WebConnectionWrapper(wc) {
                        @Override
                        public WebResponse getResponse(WebRequest request) throws IOException {
                            WebResponse response = super.getResponse(request);
                            if (request.getUrl().toExternalForm().contains("/otn/HttpZF/logdevice")) {
                                cookieUrl = request.getUrl().toExternalForm();
                            }
                            return response;

                        }
                    }

            );

            HtmlPage page = wc.getPage("https://kyfw.12306.cn/otn/leftTicket/init?linktypeid=dc");
            wc.waitForBackgroundJavaScript(3 * 1000);
        } catch (Exception e) {
            logger.error("获取失败cookie url", e);
        } finally {
            if (wc!=null) {
                wc.close();
            }
        }
        return cookieUrl;
    }


    public static void main(String[] args) {
        GetJsCookie service = new GetJsCookie();
        String url = service.getCookieUrl(null,null);
        System.out.println(url);
    }
}
