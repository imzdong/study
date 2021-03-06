package org.imzdong.tool.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class OkHttpUtils {

    private final static Logger logger = LoggerFactory.getLogger(OkHttpUtils.class);
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static final MediaType TEXT = MediaType.parse("text");
    private final static OkHttpClient client = new OkHttpClient();

    /**
     * 支持GET/POST请求
     * @param url 请求地址
     * @param headers 请求头
     * @param body 请求参数
     * @return 返回http请求
     * @throws IOException 异常
     */
    public static String http(String url, Headers headers, RequestBody body) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String resp = response.body().string();
            if(resp == null ||resp.length() == 0){
                logger.info("获取http信息异常：{}", response);
            }
            return resp;
        }
    }

    public static RequestBody getRequestBody(MediaType mediaType, String params){
        return RequestBody.create(params, mediaType);
    }

    public static Headers getHeaders(Map<String, String> headerMap){
        return Headers.of(headerMap);
    }

    public static RequestBody getEmptyRequestBody(){
        return RequestBody.create("{}".getBytes(StandardCharsets.UTF_8));
    }

}
