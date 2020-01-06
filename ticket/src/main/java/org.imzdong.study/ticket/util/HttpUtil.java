package org.imzdong.study.ticket.util;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/5
 */
public class HttpUtil {

    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private final static String HOST = "https://kyfw.12306.cn";
    private final static String CONTENT_TYPE = "application/x-www-form-urlencoded; charset=UTF-8";
    private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36";

    public static CloseableHttpClient getHttpClient(int timeOut){
        RequestConfig globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setConnectTimeout(timeOut)
                .setConnectionRequestTimeout(timeOut)
                .setSocketTimeout(timeOut)
                .build();
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader(HttpHeaders.USER_AGENT,USER_AGENT));
        CloseableHttpClient client = HttpClients.custom()
                .setDefaultHeaders(headers)
                .setDefaultCookieStore(new BasicCookieStore())
                .setDefaultRequestConfig(globalConfig)
                .build();
        return client;
    }

    public static String httpRequest(HttpClient httpClient,){

    }
}
