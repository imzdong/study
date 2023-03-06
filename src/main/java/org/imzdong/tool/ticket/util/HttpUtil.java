package org.imzdong.tool.ticket.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.Cookie;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.cookie.BasicClientCookie;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.http.client.methods.HttpRequestBase;
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

    public static CloseableHttpClient getHttpClient(){
        RequestConfig globalConfig = RequestConfig.custom()
                //.setCookieSpec(CookieSpecs.DEFAULT)
                //.setConnectTimeout(timeOut)
                //.setConnectionRequestTimeout(timeOut)
                //.setSocketTimeout(timeOut)
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
                                     HttpUriRequestBase httpRequestBase){
        try {
            return httpClient.execute(httpRequestBase,httpResponse -> {
                int statusCode = httpResponse.getCode();
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
                                          HttpUriRequestBase request) throws IOException {
        return httpClient.execute(request,response->{
            int status = response.getCode();
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

    public static void setDefaultHeader(HttpUriRequestBase request,
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
            request.setUri(new URI(REQUEST_HOST+urlConf.getRequestPath()));
        } catch (URISyntaxException e) {
            logger.error("setDefaultHeader异常：",e);
        }
    }

}
