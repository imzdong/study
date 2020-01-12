package org.imzdong.study.ticket.core;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.imzdong.study.ticket.dto.CaptchaResult;
import org.imzdong.study.ticket.util.HttpUtil;
import org.imzdong.study.ticket.util.UrlConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @description:
 * @author: Winter
 * @time: 2019年12月20日, 0020 13:55
 */
public class Captcha {

    private final static Logger logger = LoggerFactory.getLogger(Captcha.class);

    private HttpClient httpClient;

    public Captcha(HttpClient httpClient){
        this.httpClient = httpClient;
    }

    /*
     * 第二步
     * 下载验证码
     * 获取登录验证码存储本地
     * https://kyfw.12306.cn
     * /passport/captcha/captcha-image64?
     * login_site=E&module=login&rand=sjrand&1577624537878
     * &callback=jQuery191025909781158866285_1577623706238&_=1577623706239
     *
     * /passport/captcha/captcha-image64?login_site=E&module=login&rand=sjrand&{0}
     * &callback=jQuery19108016482864806321_1554298927290&_=1554298927293
     */
    public CaptchaResult getCode(String paramsCallback, String timeValue){
        logger.info("2：下载验证码");
        CaptchaResult captchaResult = new CaptchaResult();
        captchaResult.setSuccess(false);
        HttpGet httpGetCode = new HttpGet();
        HttpUtil.setDefaultHeader(httpGetCode,UrlConf.CAPTCHA_CHECK);
        try {
            URI uri = new URIBuilder(HttpUtil.REQUEST_HOST + UrlConf.CAPTCHA_IMAGE64.getRequestPath())
                    .setParameter("login_site", "E")
                    .setParameter("module", "login")
                    .setParameter("rand", "sjrand")
                    .setParameter(timeValue, "")
                    .setParameter("callback", paramsCallback)
                    .setParameter("_ ", timeValue)
                    .build();
            httpGetCode.setURI(uri);
            String requestImage = HttpUtil.httpRequestImage(httpClient, httpGetCode);
            if(StringUtils.isNotBlank(requestImage)){
                captchaResult.setSuccess(true);
                captchaResult.setValue(requestImage);
            }
        } catch (URISyntaxException | IOException e) {
            logger.error("设置验证码uri异常：",e);
            captchaResult.setMsg(e.getMessage());
        }
        return captchaResult;
    }

    /**
     * 设置callBack参数
     * @param timeValue
     * @return
     */
    public String getCallback(String timeValue){
        Random random = new Random();
        String checkCode = "jQuery1910";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<16; i++){
            stringBuilder.append(random.nextInt(9));
        }
        checkCode += stringBuilder;
        return checkCode + "_" + timeValue;
    }
    /**
     *
     * @param code
     * @return
     * https://kyfw.12306.cn/passport/captcha/captcha-check?
     * callback=jQuery191025909781158866285_1577623706238
     * &answer=190%2C102%2C59%2C117&rand=sjrand&login_site=E&_=1577623706240
     */
    public CaptchaResult checkCode(String code,String paramsCallback,String timeValue) {
        logger.info("4：校验验证码");
        CaptchaResult captchaResult = new CaptchaResult();
        String checkCodePath = String.format(UrlConf.CAPTCHA_CHECK.getRequestPath()+
                "?callback=%s&&answer=" +
                "%s&rand=sjrand&login_site=E&_=%s",
                paramsCallback,
                code, timeValue);
        HttpGet httpGetCode = new HttpGet();
        HttpUtil.setDefaultHeader(httpGetCode,UrlConf.CAPTCHA_CHECK);
        try {
            httpGetCode.setURI(new URI(HttpUtil.REQUEST_HOST+checkCodePath));
        } catch (URISyntaxException e) {
            logger.error("验证验证码异常：",e);
            captchaResult.setMsg(e.getMessage());
        }
        String response = HttpUtil.httpRequest(httpClient, httpGetCode);
        logger.info("4：转换12306验证码{}验证body:{}",code,response);
        ///**/jQuery191025909781158866285_1577623706238({"result_message":"验证码校验成功","result_code":"4"});
        if(response.contains("result_code")){
            response = response.substring(response.indexOf("(")+1,response.indexOf(")"));
            JSONObject code12306 = JSONObject.parseObject(response);
            captchaResult.setSuccess(true);
            captchaResult.setValue(code12306.getString("result_code"));//4：代表通过
        }
        return captchaResult;
    }

    public boolean image2Local(String captchaBase64Str, String imagePth){
        java.util.Base64.Decoder decoder = Base64.getDecoder();
        try {
            //Base64解码
            byte[] b = decoder.decode(captchaBase64Str);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpg图片
            FileUtils.writeByteArrayToFile(new File(imagePth),b,true);
        }catch (IOException e){
            logger.error("验证码保存到本地异常：",e);
            return false;
        }
        return true;
    }

    /*
     * 转换code
     * @param result
     * @return
     */
    public String image2Code(String result){
        logger.info("验证码坐标");
        char[] chars = result.toCharArray();
        StringBuilder post = new StringBuilder();
        String offsetsX="",offsetsY="";
        for(char ofset:chars){
            switch (ofset){
                case '1':
                    offsetsY = "77";
                    offsetsX = "40";
                    break;
                case '2':
                    offsetsY = "77";
                    offsetsX = "112";
                    break;
                case '3':
                    offsetsY = "77";
                    offsetsX = "184";
                    break;
                case '4':
                    offsetsY = "77";
                    offsetsX = "256";
                    break;
                case '5':
                    offsetsY = "149";
                    offsetsX = "40";
                    break;
                case '6':
                    offsetsY = "149";
                    offsetsX = "112";
                    break;
                case '7':
                    offsetsY = "149";
                    offsetsX = "184";
                    break;
                case '8':
                    offsetsY = "149";
                    offsetsX = "256";
                    break;
                default:
                    break;
            }
            post.append(offsetsX+",");
            post.append(offsetsY+",");
        }
        String codeStr = post.toString();
        codeStr = codeStr.substring(0,codeStr.length()-1);
        logger.info("code转换：{}",codeStr);
        return codeStr;
    }

    /*
     * 第6步认证,刷新umk
     */
    private static String sixAuth(){
        logger.info("6：校验uamtk");
        String authPath = "/passport/web/auth/uamtk";
        JSONObject params = new JSONObject();
        //"appid=otn"
        params.put("appid","otn");
        StringEntity entity = new StringEntity(params.toJSONString(), "UTF-8");
        HttpPost httpPost = new HttpPost();
        httpPost.setEntity(entity);
        String response=  HttpClientUtil.httpRequest(authPath,httpPost);
        String umk=null;
        if(response.contains("newapptk")){
            JSONObject authJson = JSONObject.parseObject(response);
            umk = authJson.getString("newapptk");
        }
        return umk;
    }
    /*
     * 第7步uam认证
     * @param uamtk
     * @return
     */
    private static void sevenUamAuth(String uamtk){
        logger.info("7：校验newapptk");
        String uamAuthPath = "/otn/uamauthclient";
        JSONObject params = new JSONObject();
        //{"tk": uamtk}
        params.put("tk",uamtk);
        StringEntity entity = new StringEntity(params.toJSONString(), "UTF-8");
        HttpPost httpPost = new HttpPost();
        httpPost.setEntity(entity);
        HttpClientUtil.httpRequest(uamAuthPath,httpPost);
    }

    /*
     * 第七步检查用户是否登录
     * @return
     */
    private static String eightCheckUser(){
        String checkUserUrl = "/otn/login/checkUser";
        //data = {"_json_att": ""}
        String body = String.format("_json_att=%s"
                ,"");
        HttpPost httpPost = new HttpPost();
        httpPost.setEntity(new StringEntity(body, "UTF-8"));
        String response = HttpClientUtil.httpRequest(checkUserUrl,httpPost);
        return response;
    }
}
