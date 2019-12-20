package org.imzdong.study.ticket.core;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;


/**
 * @description:
 * @author: Winter
 * @time: 2019年12月20日, 0020 13:55
 */
public class Login {

    private final static Logger logger = LoggerFactory.getLogger(Login.class);
    private final static HttpGet httpGet = new HttpGet();
    /*
     * 第一步初始化登录
     */
    private static void firstInit(){
        logger.info("1：初始化登录");
        String initPath = "/otn/leftTicket/init";
        HttpClientUtil.httpRequest(initPath,httpGet);
    }
    /*
     * 第二步
     * 下载验证码
     * 获取登录验证码存储本地
     */
    private static void secondGetCode(){
        logger.info("2：下载验证码");
        Random random = new Random();//默认构造方法
        int randomNum = random.nextInt(1000000);
        String codePath = String.format("/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&%d",randomNum);
        HttpClientUtil.httpRequest(codePath,httpGet);
    }
    /*
     * 转换code
     * @param result
     * @return
     */
    private static String thirdInputCode(String result){
        logger.info("3：手动输入验证码");
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

    private static int fourthCheckCode(String code) {
        logger.info("4：校验验证码");
        String checkCodePath = "/passport/captcha/captcha-check";
        HttpPost httpPost = new HttpPost();
        JSONObject params = new JSONObject();
        //"rand=sjrand&login_site=E&answer="+code;
        params.put("rand","sjrand");
        params.put("login_site","E");
        params.put("answer",code);
        StringEntity entity = new StringEntity(params.toJSONString(), "UTF-8");
        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(entity);
        String response = HttpClientUtil.httpRequest(checkCodePath,httpPost);
        logger.info("4：转换12306验证码{}验证body:{}",code,response);
        if(response.contains("result_code")){
            JSONObject code12306 = JSONObject.parseObject(response);
            return code12306.getInteger("result_code");//4：代表通过
        }else {
            return 0;
        }
    }

    /*
     * 第五步初始化登录
     */
    private static String fifthWebLogin(String name,String pwd) {
        logger.info("5：用户名密码登陆");
        String loginPath = "/passport/web/login";
        JSONObject params = new JSONObject();
        //"username=%s&password=%s&appid=otn"
        params.put("username",name);
        params.put("password",pwd);
        params.put("appid","otn");
        StringEntity entity = new StringEntity(params.toJSONString(), "UTF-8");
        HttpPost httpPost = new HttpPost();
        httpPost.setEntity(entity);
        String response=  HttpClientUtil.httpRequest(loginPath,httpPost);
        //{"result_message":"登录成功","result_code":0,"uamtk":"yYyKPBEQh8re5T4otBKA5Mj7GCOF1R5cl65R9Qafh2h0"}
        JSONObject loginJson = JSONObject.parseObject(response);
        if(StringUtils.isNotBlank(loginJson.getString("uamtk"))){
            return loginJson.getString("uamtk");
        }
        return null;
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
}
