package org.imzdong.study.ticket.core;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Winter
 * @time: 2019年12月20日, 0020 14:23
 */
public class HttpClientUtil {
    private final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static CloseableHttpClient httpClient;
    private static CookieStore cookieStore;
    private static RequestConfig globalConfig;
    private static List<Header> headers;
    private final static String HOST = "https://kyfw.12306.cn";
    private final static String CONTENT_TYPE = "application/x-www-form-urlencoded; charset=UTF-8";
    private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36";
    private HttpClientUtil(){}
    static {
        headers = new ArrayList<>();
        headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE,CONTENT_TYPE));
        headers.add(new BasicHeader(HttpHeaders.ACCEPT,"application/json, text/plain, */*"));
        headers.add(new BasicHeader(HttpHeaders.CONNECTION,"keep-alive"));
        headers.add(new BasicHeader(HttpHeaders.USER_AGENT,USER_AGENT));
        headers.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING,"gzip, deflate"));
        globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .build();
        cookieStore = new BasicCookieStore();
        httpClient = HttpClients.custom()
                .setDefaultHeaders(headers)
                .setDefaultCookieStore(cookieStore)
                .setDefaultRequestConfig(globalConfig)
                .build();
    }
    /**
     * http请求执行
     * @param requestPath
     * @param request
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static String httpRequest(String requestPath, HttpRequestBase request) {
        try {
            request.setURI(new URI(HOST+requestPath));
            CloseableHttpResponse execute = httpClient.execute(request);
            List<Cookie> cookies = cookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                logger.info("local cookie:{}",JSONObject.toJSONString(cookies.get(i)));
            }
            int statusCode = execute.getStatusLine().getStatusCode();
            if(statusCode >= 200 && statusCode < 300){
                String body = EntityUtils.toString(execute.getEntity());
                //logger.info("执行http结果：{}",body);
                return body;
            }
            throw new ClientProtocolException("Unexpected response status: " + statusCode);
        } catch (IOException | URISyntaxException e) {
            logger.error("执行http请求异常：",e);
            return "";
        }
    }

    /**
     * http请求执行
     * @param requestPath
     * @param request
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void httpRequestImage(String requestPath, HttpRequestBase request, String imagePth) {
        try {
            request.setURI(new URI(HOST+requestPath));
            httpClient.execute(request,response->{
                logger.info("执行http结果：{}",response);
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    InputStream inputStream= entity.getContent(); //获取链接返回的流
                    FileUtils.copyInputStreamToFile(inputStream,new File(imagePth));
                    return "";
                } else {
                    logger.error("执行http请求返回码：{}",status);
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            });
        } catch (IOException | URISyntaxException e) {
            logger.error("执行http请求异常：",e);
        }
    }
}
