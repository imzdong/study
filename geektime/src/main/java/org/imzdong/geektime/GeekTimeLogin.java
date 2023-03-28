package org.imzdong.geektime;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.imzdong.geektime.util.OkHttpUtils;

import java.io.IOException;
import java.util.Map;

/**
 * 登录极客时间
 * @author dong.zhou
 * @since 2022/8/9 10:00
 */
@Slf4j
public class GeekTimeLogin {

    private String account;
    private String password;

    public GeekTimeLogin(String account, String password){
        this.account = account;
        this.password = password;
    }

    public boolean login(){
        String url = GeekTimeConstant.loginUrl;
        Map<String, String> headerMap = GeekTimeConstant.headers;
        headerMap.put("Host", "account.geekbang.org");
        headerMap.put("Referer", "https://account.geekbang.org/signin?redirect=https%3A%2F%2Fwww.geekbang.org%2F");
        //{"desc":true,"expire":1,"last_learn":0,"learn_status":0,"prev":0,"size":20,
        // "sort":1,"type":"","with_learn_count":1}
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("country", "86");
        bodyJson.put("cellphone", this.account);
        bodyJson.put("password", this.password);
        bodyJson.put("learn_status", 0);
        bodyJson.put("captcha", "");
        bodyJson.put("remember", 1);
        bodyJson.put("platform", 3);
        bodyJson.put("appid", 1);
        try {
            String resp = OkHttpUtils.http(url, OkHttpUtils.getHeaders(headerMap),
                OkHttpUtils.getRequestBody(OkHttpUtils.JSON, bodyJson.toJSONString()));
            JSONObject result = JSONObject.parseObject(resp);
            if(result != null && result.containsKey("code")&&"0".equals(result.getString("code"))){
                log.info("login info : {}", resp);
                return true;
            }else {
                log.error("登录返回code：{}，返回错误信息：{}",
                    result != null?result.getString("code"):resp,
                    result != null?result.getString("error"):"");
            }
        } catch (IOException e) {
            log.error("登录异常：", e);
        }
        return false;
    }

}
