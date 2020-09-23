package org.imzdong.study.ticket.core;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.imzdong.study.ticket.dto.LoginResult;
import org.imzdong.study.ticket.util.HttpUtil;
import org.imzdong.study.ticket.util.UrlConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @description:
 * @author: Winter
 * @time: 2019年12月20日, 0020 13:55
 */
public class Login {

    private final static Logger logger = LoggerFactory.getLogger(Login.class);

    private HttpClient httpClient;

    public Login(HttpClient httpClient){
        this.httpClient = httpClient;
    }

    public LoginResult initLogin(){
        GetJsCookie getJsCookie = new GetJsCookie();
        String logDeviceUrl = getJsCookie.getCookieUrl(null, null);
        if(StringUtils.isBlank(logDeviceUrl)){
            return new LoginResult(false,"获取logDeviceUrl失败");
        }
        if(!logDevice(logDeviceUrl)){
            return new LoginResult(false,"设置exp和dfp失败");
        }
        /*if(!loginConf()){
            return new LoginResult(false,"获取用户登录状态失败");
        }*/
        return new LoginResult(true,"初始化成功");
    }

    /**
     * web用户名、密码登录
     * @param name
     * @param pwd
     * @param code
     * @return
     */
    public LoginResult webLogin(String name,String pwd, String code) {
        logger.info("用户名密码登陆");
        LoginResult loginResult = new LoginResult();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", name));
        params.add(new BasicNameValuePair("password", pwd));
        params.add(new BasicNameValuePair("appid", "otn"));
        params.add(new BasicNameValuePair("answer", code));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);
        HttpPost httpPost = new HttpPost();
        httpPost.setEntity(entity);
        HttpUtil.setDefaultHeader(httpPost,UrlConf.WEB_LOGIN);
        String response=  HttpUtil.httpRequest(httpClient,httpPost);
        //{"result_message":"登录成功","result_code":0,"uamtk":"yYyKPBEQh8re5T4otBKA5Mj7GCOF1R5cl65R9Qafh2h0"}
        logger.info("login:{}",response);
        JSONObject loginJson = JSONObject.parseObject(response);
        if(StringUtils.isNotBlank(loginJson.getString("uamtk"))){
            loginResult.setSuccess(true);
        }
        return loginResult;
    }

    /*
     * 检查用户是否登录
     * @return
     */
    public boolean checkUser(){
        String body = String.format("_json_att=%s","");
        HttpPost httpPost = new HttpPost();
        HttpUtil.setDefaultHeader(httpPost,UrlConf.CHECK_USER);
        httpPost.setEntity(new StringEntity(body, "UTF-8"));
        String response = HttpUtil.httpRequest(httpClient,httpPost);//HttpClientUtil.httpRequest(checkUserUrl,httpPost);
        JSONObject responseJson = JSONObject.parseObject(response);
        boolean status = responseJson.getBoolean("status");
        boolean flag = responseJson.getJSONObject("data").getBoolean("flag");
        // success
        if (status && flag){
            return true;
        }
        return false;
    }
    /**
     * 设置cookie
     * @param logDeviceUrl
     * @return
     * @throws Exception
     */
    private boolean logDevice(String logDeviceUrl){
        logger.info("初始化登录：1.2、设置cookie（RAIL_EXPIRATION+RAIL_DEVICEID）");
        HttpGet httpGetLog = new HttpGet();
        httpGetLog.addHeader("User-Agent", HttpUtil.USER_AGENT);
        try {
            httpGetLog.setURI(new URI(logDeviceUrl));
        } catch (URISyntaxException e) {
            logger.error("logDevice异常：",e);
        }
        String logDevices = HttpUtil.httpRequest(httpClient, httpGetLog);
        if(logDevices.contains("callbackFunction")){
            String str = logDevices.substring(logDevices.indexOf("{"),
                    logDevices.indexOf("}")+1);
            JSONObject obj = JSONObject.parseObject(str);
            HttpUtil.addRailCookies(obj.getString("exp"), obj.getString("dfp"));
            return true;
        }
        return false;
    }

    /**
     * 初始化登录状态
     * @return
     */
    private boolean loginConf(){
        logger.info("初始化登录：1.3、登录状态");
        HttpPost httpPost = new HttpPost();
        HttpUtil.setDefaultHeader(httpPost,UrlConf.LOGIN_CONF);
        String confStr = HttpUtil.httpRequest(httpClient, httpPost);
        logger.info("first5Conf:{}",confStr);
        JSONObject confJson = JSONObject.parseObject(confStr);
        if(confJson.containsKey("data")){
            JSONObject data = confJson.getJSONObject("data");
            //"is_login_passCode": "Y",
            if("Y".equals(data.getString("is_login_passCode"))){
                return true;
            }
        }
        return false;
    }

    private void loginBanner(){
        logger.info("初始化登录：1.4、登录banner");
        HttpGet httpPost = new HttpGet();
        HttpUtil.setDefaultHeader(httpPost,UrlConf.LOGIN_BANNER);
        String confStr = HttpUtil.httpRequest(httpClient, httpPost);
        logger.info("LoginBanner:{}",confStr);
    }

    private void uamtkStatic(){
        logger.info("初始化登录：1.5、uamtkStatic初始化");
        HttpPost httpPost = new HttpPost();
        HttpUtil.setDefaultHeader(httpPost,UrlConf.UAMTK_STATIC);
        //appid=otn
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("appid", "otn"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);
        httpPost.setEntity(entity);
        String confStr = HttpUtil.httpRequest(httpClient, httpPost);
        logger.info("UamtkStatic:{}",confStr);
    }

}
