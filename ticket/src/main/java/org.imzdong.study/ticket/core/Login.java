package org.imzdong.study.ticket.core;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
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
    public static void firstInit(){
        logger.info("1：初始化登录");
        String initPath = "/otn/leftTicket/init";
        HttpClientUtil.httpRequest(initPath,httpGet);
    }
    /*
     * 第一步初始化登录
     */
    public static void first2UmtStatic(){
        logger.info("2.3：初始化静态页面");
        String initPath = "/passport/web/auth/uamtk-static";
        HttpClientUtil.httpRequest(initPath,httpGet);
    }

    public static String first3LoginConf(){
        logger.info("1.2：初始化登录");
        String initPath = "/otn/login/conf";
        //{"validateMessagesShowId":"_validatorMessage","status":true,"httpstatus":200,"data":{"isstudentDate":false,"is_login_passCode":"Y","is_sweep_login":"Y","psr_qr_code_result":"N","login_url":"resources/login.html","studentDate":["2019-06-01","2019-09-30","2019-12-01","2019-12-31","2020-01-01","2020-03-31"],"stu_control":30,"is_uam_login":"Y","is_login":"N","other_control":30},"messages":[],"validateMessages":{}}
        return HttpClientUtil.httpRequest(initPath,httpGet);
    }
    /*
     * 第二步
     * 下载验证码
     * 获取登录验证码存储本地
     * https://kyfw.12306.cn
     * /passport/captcha/captcha-image64?
     * login_site=E&module=login&rand=sjrand&1577624537878
     * &callback=jQuery191025909781158866285_1577623706238&_=1577623706239
     */
    public static void secondGetCode(){
        logger.info("2：下载验证码");
        Random random = new Random();//默认构造方法
        int randomNum = random.nextInt(1000000);//captcha-image64
        String codePath = String.format("/passport/captcha/captcha-image?" +
                "login_site=E&module=login&rand=sjrand&%d" +
                "&callback=jQuery191025909781158866285_1577623706238&_=%d",
                randomNum, random.nextInt(1000000));
        HttpClientUtil.httpRequestImage(codePath,httpGet,"D:\\WorkSpace\\result\\20191221\\random.png");
        //HttpClientUtil.httpRequest(codePath,httpGet);
    }
    /*
     * 转换code
     * @param result
     * @return
     */
    public static String thirdInputCode(String result){
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

    /**
     *
     * @param code
     * @return
     * https://kyfw.12306.cn/passport/captcha/captcha-check?
     * callback=jQuery191025909781158866285_1577623706238
     * &answer=190%2C102%2C59%2C117&rand=sjrand&login_site=E&_=1577623706240
     */
    public static int fourthCheckCode(String code) {
        logger.info("4：校验验证码");
        String checkCodePath = String.format("/passport/captcha/captcha-check?" +
                "callback=jQuery191025909781158866285_1577623706238&&answer=" +
                "%s&rand=sjrand&login_site=E&_=%d",
                code, new Random().nextInt(1000000));
        String response = HttpClientUtil.httpRequest(checkCodePath, httpGet);
        logger.info("4：转换12306验证码{}验证body:{}",code,response);
        ///**/jQuery191025909781158866285_1577623706238({"result_message":"验证码校验成功","result_code":"4"});
        if(response.contains("result_code")){
            response = response.substring(response.indexOf("(")+1,response.indexOf(")"));
            JSONObject code12306 = JSONObject.parseObject(response);
            return code12306.getInteger("result_code");//4：代表通过
        }else {
            return 0;
        }
    }

    /*
     * 第五步初始化登录
     */
    public static String fifthWebLogin(String name,String pwd, String code) {
        logger.info("5：用户名密码登陆");
        String loginPath = "/passport/web/login";
        /*JSONObject params = new JSONObject();
        //"username=%s&password=%s&appid=otn"
        params.put("username",name);
        params.put("password",pwd);
        params.put("appid","otn");
        params.put("answer",code);*/
        //StringEntity entity = new StringEntity(params.toJSONString(), "UTF-8");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", name));
        params.add(new BasicNameValuePair("password", pwd));
        params.add(new BasicNameValuePair("appid", "otn"));
        params.add(new BasicNameValuePair("answer", code));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);
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
    public static String sixAuth(){
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
    public static void sevenUamAuth(String uamtk){
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
    public static String eightCheckUser(){
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
