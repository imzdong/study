package org.imzdong.tool.ticket.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @description:
 * @author: Winter
 * @time: 2019/12/29
 */
public class JdkHttpUtil {

    private final static String POST = "POST";
    private final static String GET = "GET";
    private static String J_SESSION_ID = "";
    private final static String CONTENT_TYPE = "application/x-www-form-urlencoded; charset=UTF-8";

    public static String httpGet(String getRequest) throws Exception{
        URL url = new URL(getRequest);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", CONTENT_TYPE);
        conn.setRequestMethod(GET);
        conn.setRequestProperty("Cookie","JSESSIONID=" + J_SESSION_ID);
        InputStream inputStream = conn.getInputStream();
        getCookie(conn);
        String body = IOUtils.toString(inputStream, "UTF-8");
        inputStream.close();
        conn.disconnect();
        return body;
    }

    public static void httpGetImage(String getRequest, String imagePath) throws Exception{
        URL url = new URL(getRequest);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(GET);
        con.setRequestProperty("Content-Type", CONTENT_TYPE);
        con.setRequestProperty("Cookie","JSESSIONID=" + J_SESSION_ID);
        InputStream inputStream = con.getInputStream();
        getCookie(con);
        FileUtils.copyInputStreamToFile(inputStream,new File(imagePath));
        inputStream.close();
        con.disconnect();
    }

    public static String httpPost(String postRequest, Map<String, Object> params) throws Exception{
        URL url = new URL(postRequest);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(POST);
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestProperty("Cookie","JSESSIONID=" + J_SESSION_ID);
        con.setRequestProperty("Content-Type", CONTENT_TYPE);
        if (params != null && params.size() > 0) {
            StringBuffer postParams = new StringBuffer();
            for (Map.Entry<String, Object> e : params.entrySet()) {
                postParams.append(e.getKey());
                postParams.append("=");
                postParams.append(e.getValue());
                postParams.append("&");
            }
            // 获取URLConnection对象对应的输出流
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            if (postParams.length() > 0) {
                // 发送请求参数
                osw.write(postParams.substring(0, postParams.length() - 1));
                // flush输出流的缓冲
                osw.flush();
            }
            osw.close();
        }
        InputStream inputStream = con.getInputStream();
        getCookie(con);
        String body = IOUtils.toString(inputStream, "UTF-8");
        inputStream.close();
        con.disconnect();
        return body;
    }

    private static void getCookie(HttpURLConnection conn){
        String serverCookies = conn.getHeaderField("Set-Cookie");
        if(serverCookies != null){
            String[] cookies = serverCookies.split(";");
            for(String s : cookies){
                s = s.trim();
                if(s.split("=")[0].equals("JSESSIONID")){
                    J_SESSION_ID = s.split("=")[1];
                    break;
                }
            }
        }
    }
}
