package org.imzdong.study.ticket.core;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: Winter
 * @time: 2019年12月20日, 0020 13:55
 */
public class Login {

    private final static Logger logger = LoggerFactory.getLogger(Login.class);
    private final static HttpGet httpGet = new HttpGet();
    private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36";

    /*
     * 第一步初始化登录
     * https://kyfw.12306.cn/otn/login/init
     * /otn/leftTicket/init
     */
    public static void firstInit(){
        logger.info("1：初始化登录");
        String initPath = "/otn/leftTicket/init";
        HttpClientUtil.httpRequest(initPath,httpGet);
    }
    /*
     * 第一步初始化登录
     * /passport/web/auth/uamtk-static
     * /otn/HttpZF/GetJS
     */
    public static String first2GetJs(){
        logger.info("2.3：初始化静态页面");
        String initPath = "/otn/HttpZF/GetJS";
        String jsStr = HttpClientUtil.httpRequest(initPath, httpGet);
        String reg = "algID\\\\x3d(.*?)\\\\x26";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(jsStr);
        if(matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    /**
     * @return
     * /otn/login/conf
     */
    public static void first3LogDevice(String algID, String otherParams){
        logger.info("1.2：初始化登录");
        String logDevice = String.format("/otn/HttpZF/logdevice?" +
                "algID=%s&timestamp=%d%s", algID,
                System.currentTimeMillis(), otherParams);
        //{"validateMessagesShowId":"_validatorMessage","status":true,"httpstatus":200,"data":{"isstudentDate":false,"is_login_passCode":"Y","is_sweep_login":"Y","psr_qr_code_result":"N","login_url":"resources/login.html","studentDate":["2019-06-01","2019-09-30","2019-12-01","2019-12-31","2020-01-01","2020-03-31"],"stu_control":30,"is_uam_login":"Y","is_login":"N","other_control":30},"messages":[],"validateMessages":{}}
        String logDevices = HttpClientUtil.httpRequest(logDevice, httpGet);
        if(logDevices.contains("callbackFunction")){
            String str = logDevices.substring(logDevices.indexOf("{"),
                    logDevices.indexOf("}")+1);
            JSONObject obj = JSONObject.parseObject(str);
            HttpClientUtil.addRailCookies(obj.getString("exp"), obj.getString("dfp"));
        }
    }
    /**
     * 获取device参数
     * @return
     */
    public static String first4GetLogDeviceParams(){
        Map<String,String> dataMap = new HashMap();
        dataMap.put("adblock","0");
        dataMap.put("browserLanguage","en-US");
        dataMap.put("jsFonts","c227b88b01f5c513710d4b9f16a5ce52");
        dataMap.put("javaEnabled","0");
        dataMap.put("flashVersion","0");
        dataMap.put("doNotTrack","unknown");
        dataMap.put("custID","133");
        dataMap.put("cookieEnabled","1");
        dataMap.put("plugins","d22ca0b81584fbea62237b14bd04c866");
        dataMap.put("platform","WEB");
        dataMap.put("os","MacIntel");
        dataMap.put("mimeTypes","52d67b2a5aa5e031084733d5006cc664");
        dataMap.put("localCode","3232236206");
        dataMap.put("scrAvailSize",new Random().nextInt(1000)+"x1920");
        dataMap.put("srcScreenSize","24xx1080x1920");
        dataMap.put("storeDb","i1l1o1s1");
        dataMap.put("timeZone","-8");
        dataMap.put("touchSupport","99115dfb07133750ba677d055874de87");
        try {
            dataMap.put("userAgent",
                    URLEncoder.encode(USER_AGENT
                            /*"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0." +
                    new Random().nextInt(7000)+".0 Safari/537.36"*/
                            ,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("编码失败",e);
        }
        dataMap.put("webSmartID","f4e3b7b14cc647e30a6267028ad54c56");
        Map<String,String> dataTransMap = new HashMap();
        dataTransMap.put("browserVersion","d435");
        dataTransMap.put("touchSupport","wNLf");
        dataTransMap.put("systemLanguage","e6OK");
        dataTransMap.put("scrWidth","ssI5");
        dataTransMap.put("openDatabase","V8vl");
        dataTransMap.put("scrAvailSize","TeRS");
        dataTransMap.put("hasLiedResolution","3neK");
        dataTransMap.put("hasLiedOs","ci5c");
        dataTransMap.put("timeZone","q5aJ");
        dataTransMap.put("userAgent","0aew");
        dataTransMap.put("userLanguage","hLzX");
        dataTransMap.put("jsFonts","EOQP");
        dataTransMap.put("scrAvailHeight","88tV");
        dataTransMap.put("browserName","-UVA");
        dataTransMap.put("cookieCode","VySQ");
        dataTransMap.put("online","9vyE");
        dataTransMap.put("scrAvailWidth","E-lJ");
        dataTransMap.put("flashVersion","dzuS");
        dataTransMap.put("scrDeviceXDPI","3jCe");
        dataTransMap.put("srcScreenSize","tOHY");
        dataTransMap.put("storeDb","Fvje");
        dataTransMap.put("doNotTrack","VEek");
        dataTransMap.put("mimeTypes","jp76");
        dataTransMap.put("sessionStorage","HVia");
        dataTransMap.put("cookieEnabled","VPIf");
        dataTransMap.put("os","hAqN");
        dataTransMap.put("hasLiedLanguages","j5po");
        dataTransMap.put("hasLiedBrowser","2xC5");
        dataTransMap.put("webSmartID","E3gR");
        dataTransMap.put("appcodeName","qT7b");
        dataTransMap.put("javaEnabled","yD16");
        dataTransMap.put("plugins","ks0Q");
        dataTransMap.put("appMinorVersion","qBVW");
        dataTransMap.put("cpuClass","Md7A");
        dataTransMap.put("indexedDb","3sw-");
        dataTransMap.put("adblock","FMQw");
        dataTransMap.put("localCode","lEnu");
        dataTransMap.put("browserLanguage","q4f3");
        dataTransMap.put("scrHeight","5Jwy");
        dataTransMap.put("localStorage","XM7l");
        dataTransMap.put("historyList","kU5z");
        dataTransMap.put("scrColorDepth","qmyu");
        String d = "";
        Map<String, String> params = new HashMap<>();
        Set<Map.Entry<String, String>> entries = dataMap.entrySet();
        for(Map.Entry<String,String> entry:entries){
            String key = entry.getKey();
            String item = entry.getValue();
            d += (key + item);
            key = dataTransMap.get(key)!=null?dataTransMap.get(key):key;
            params.put(key,item);
        }

        int dl = d.length();
        int df = (dl%3==0)?dl/3:dl/3+1;
        if(dl >= 3){
            d = d.substring(df,2 * df)+d.substring(2 * df,dl)
                    +d.substring(0,df);
        }
        dl = d.length();
        df = (dl%3==0)?dl/3:dl/3+1;
        if(dl >= 3) {
            d = d.substring(2 * df,dl)+d.substring(0,df)
            +d.substring(1 * df,2 * df);
        }
        d = data2Str(d);
        d = data2Str(d);
        d = data2Str(d);
        String dataStr = encodeStr(d);
        params.put("hashCode",dataStr);
        StringBuffer paramsStr = new StringBuffer();
        for(Map.Entry<String,String> entry:params.entrySet()){
            paramsStr.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        return paramsStr.toString();
    }
    private static String encodeStr(String dStr){
        String encodeS = Base64.encodeBase64String(DigestUtils.sha256(dStr.getBytes()));
        return Base64.encodeBase64String(encodeS.getBytes())
                .replaceAll("\\+", "-")
                .replaceAll("/", "_")
                .replaceAll("=", "");
    }

    private static String data2Str(String d){
        int b = d.length();
        if(b % 2 == 0){
            return d.substring(b/2,b) + d.substring(0,b/2);
        } else{
            return d.substring(b/2 + 1,b) + d.substring(b/2) + d.substring(0,b/2);
        }
    }

    /*
     * 第一步初始化登录
     * https://kyfw.12306.cn/otn/login/init
     * /otn/leftTicket/init
     */
    public static void first5Conf(){
        logger.info("1：初始化登录");
        String initPath = "/otn/login/conf";
        HttpClientUtil.httpRequest(initPath,httpGet);
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
    public static void secondGetCode(){
        logger.info("2：下载验证码");
        String codePath = String.format("/passport/captcha/captcha-image64?" +
                "login_site=E&module=login&rand=sjrand&%d" +
                "&callback=jQuery191025909781158866285_1577623706238&_=%d",
                System.currentTimeMillis(),System.currentTimeMillis());
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
