package org.imzdong.study.ticket.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/5
 */
public class HttpUtil {

    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String GET = "GET";
    public static String POST = "POST";
    private static CookieStore cookieStore;
    private static final String HOST = "kyfw.12306.cn";
    public final static String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded; charset=UTF-8";
    public final static String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";
    public static final String REQUEST_HOST = "https://kyfw.12306.cn";
    public final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36";

    public static CloseableHttpClient getHttpClient(int timeOut){
        RequestConfig globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setConnectTimeout(timeOut)
                .setConnectionRequestTimeout(timeOut)
                .setSocketTimeout(timeOut)
                .build();
        List<Header> headers = new ArrayList<>();
        cookieStore = new BasicCookieStore();
        headers.add(new BasicHeader(HttpHeaders.USER_AGENT,USER_AGENT));
        CloseableHttpClient client = HttpClients.custom()
                .setDefaultHeaders(headers)
                .setDefaultCookieStore(cookieStore)
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

    /**
     * http请求执行
     * @param request
     * @return
     * @throws IOException
     */
    public static String httpRequestImage(HttpClient httpClient,
                                          HttpRequestBase request) throws IOException {
        return httpClient.execute(request,response->{
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                List<Cookie> cookies = cookieStore.getCookies();
                for (int i = 0; i < cookies.size(); i++) {
                    logger.info("httpRequestImage cookie:{}", JSONObject.toJSONString(cookies.get(i)));
                }
                HttpEntity entity = response.getEntity();
                String body = EntityUtils.toString(entity);
                logger.info("获取image结果：{}",body);
                String reg = "\\{.*\\}";
                Pattern pattern = Pattern.compile(reg);
                Matcher matcher = pattern.matcher(body);
                while (matcher.find()) {
                    JSONObject obj = JSON.parseObject(matcher.group(0));
                    return obj.getString("image");
                }
                throw new RuntimeException("获取验证码失败");
            } else {
                logger.error("执行http请求返回码：{}",status);
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        });
    }

    public static void addRailCookies(String exp, String dfp){
        //{"domain":"kyfw.12306.cn","name":"route","path":"/","persistent":false,
        // "secure":false,"value":"6f50b51faa11b987e576cdb301e545c4","version":0}
        BasicClientCookie expCookie = new BasicClientCookie("RAIL_EXPIRATION", exp);
        expCookie.setDomain("kyfw.12306.cn");
        expCookie.setPath("/");
        cookieStore.addCookie(expCookie);
        BasicClientCookie dfpCookie = new BasicClientCookie("RAIL_DEVICEID", dfp);
        dfpCookie.setDomain("kyfw.12306.cn");
        dfpCookie.setPath("/");
        cookieStore.addCookie(dfpCookie);
    }

    public static void setDefaultHeader(HttpRequestBase request,
                                          UrlConf urlConf){
        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("Host", HOST);
        if(urlConf.isJson()) {
            request.addHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON);
        }else {
            request.addHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_FORM);
        }
        if(urlConf.isXml()){
            request.addHeader("X-Requested-With", "XMLHttpRequest");
        }
        try {
            request.setURI(new URI(REQUEST_HOST+urlConf.getRequestPath()));
        } catch (URISyntaxException e) {
            logger.error("setDefaultHeader异常：",e);
        }
    }

}
