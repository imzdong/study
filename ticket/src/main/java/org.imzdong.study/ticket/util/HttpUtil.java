package org.imzdong.study.ticket.util;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/5
 */
public class HttpUtil {

    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public final static String GET = "GET";
    public final static String POST = "POST";
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

    /**
     * 执行http请求
     * @param httpClient client
     * @param httpRequestBase 实际请求
     * @return
     */
    public static String httpRequest(HttpClient httpClient,
                                     HttpRequestBase httpRequestBase){
        try {
            return httpClient.execute(httpRequestBase,httpResponse -> {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if(statusCode >= 200 && statusCode < 300){
                    return EntityUtils.toString(httpResponse.getEntity());
                }
                logger.error("执行请求未返回正常码值:{}",httpResponse.getEntity());
                throw new RuntimeException(String.format("请求返回码：%d",statusCode));
            });
        } catch (IOException e) {
            logger.error("执行http请求异常：",e);
            throw new RuntimeException(e);
        }
    }

}
