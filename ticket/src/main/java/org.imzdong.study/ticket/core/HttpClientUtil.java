package org.imzdong.study.ticket.core;

import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
    private final static String HOST = "https://kyfw.12306.cn";
    private final static String CONTENT_TYPE = "application/x-www-form-urlencoded; charset=UTF-8";
    private HttpClientUtil(){}
    static {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE,CONTENT_TYPE));
        RequestConfig globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .build();
        httpClient = HttpClients.custom()
                .setDefaultHeaders(headers)
                .setDefaultRequestConfig(globalConfig)
                .build();
        //httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);
    }
    public static HttpClient getHttpClient(){
        return httpClient;
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
            return httpClient.execute(request,response->{
                logger.info("执行http结果：{}",response);
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    logger.error("执行http请求返回码：{}",status);
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            });
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
                    /*FileOutputStream fileOutputStream = new FileOutputStream(imagePth);
                    byte[] inputs = new byte[1024];
                    while (inputStream.read(inputs)!=-1){
                        fileOutputStream.write(inputs);
                        fileOutputStream.flush();
                    }
                    inputStream.close();
                    fileOutputStream.close();*/
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

    public static void main(String[] args) throws Exception{
        InputStream in = new URL( "https://img-bss.csdnimg.cn/201912130305036460.png" ).openStream();
        try {

            InputStreamReader inR = new InputStreamReader(in);
            BufferedReader buf = new BufferedReader(inR);
            String line;
            while ( ( line = buf.readLine() ) != null ) {
                System.out.println( line );
            }
        } finally {
            in.close();
        }


        String path = "https://img-bss.csdnimg.cn/201912130305036460.png";
        String imagePath = "D:\\WorkSpace\\result\\20191221\\123.png";
        HttpGet httpGet = new HttpGet();
        httpRequestImage(path,httpGet,imagePath);
    }
}
