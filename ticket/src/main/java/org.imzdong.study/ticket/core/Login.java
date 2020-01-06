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
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
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
    private static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36";
    private static final String HOST = "kyfw.12306.cn";


    public void initLogin(){
        GetJsCookie getJsCookie = new GetJsCookie();
        String logDeviceUrl = getJsCookie.getCookieUrl(null, null);

    }

    public static boolean logDevice(String logDeviceUrl) throws Exception{
        logger.info("初始化登录：1.2、设置cookie（RAIL_EXPIRATION+RAIL_DEVICEID）");
        HttpGet httpGetLog = new HttpGet();
        httpGetLog.addHeader("User-Agent", USER_AGENT);
        httpGetLog.addHeader("Host", HOST);
        httpGetLog.addHeader("X-Requested-With", "XMLHttpRequest");
        httpGetLog.addHeader("Referer", "https://kyfw.12306.cn/otn/resources/login.html");
        httpGetLog.setURI(new URI(logDeviceUrl));
        String logDevices = HttpClientUtil.httpRequest(null, httpGetLog);
        if(logDevices.contains("callbackFunction")){
            String str = logDevices.substring(logDevices.indexOf("{"),
                    logDevices.indexOf("}")+1);
            JSONObject obj = JSONObject.parseObject(str);
            HttpClientUtil.addRailCookies(obj.getString("exp"), obj.getString("dfp"));
            return true;
        }
        return false;
    }

    public static void main(String[] args)throws Exception {
        //firstInit();
        //String first2GetJs = first2GetJs();
        //first3LogDevice(first2GetJs);
        String logUrl = "https://kyfw.12306.cn/otn/HttpZF/logdevice?algID=L983yIjUNc&hashCode=99-NcrPjyraaQFW0p9teJtxXVpOviwD3_096cXysFDA&FMQw=0&q4f3=en-US&VPIf=1&custID=133&VEek=unknown&dzuS=0&yD16=0&EOQP=d41d8cd98f00b204e9800998ecf8427e&jp76=d41d8cd98f00b204e9800998ecf8427e&hAqN=Win32&platform=WEB&ks0Q=d41d8cd98f00b204e9800998ecf8427e&TeRS=768x1024&tOHY=24xx768x1024&Fvje=l1s1&q5aJ=-8&wNLf=99115dfb07133750ba677d055874de87&0aew=Mozilla/5.0%20(Windows%20NT%206.1;%20Win64;%20x64)%20AppleWebKit/537.36%20(KHTML,%20like%20Gecko)%20Chrome/76.0.3809.100%20Safari/537.36&E3gR=a7509a2229798830c663640943369109&timestamp=1578194481920";
        //first3LogDeviceNew(logUrl);
        /*String RAIL_EXPIRATION = "1578243162867";//"1576651914389";
        String RAIL_DEVICEID = "m2xe4uXpazTrfygBW8zccA_CVOci_EpUt1arq6IZRCAf_4wJP4SPIQEtnvJkPg0-V_jwCN5_PtzXF8NFBTR99t8jyP3D0vkWA3tfcLvmmYObBklp-obu80KQsOaCuqIn_qBjy7vKF1p9puTrDK4LG8xrOG6y_bLv";//"lBJStCNl0YGo_HVkGtwOo2LWziXcwzpIk5gc2vAILNYdRfaeZ04nJtZ1JZwgQIssMDksn10rAz6Hz-bekeufhAusaKJId8f2BCg05ocgrzc8-chv8h4IB-lQ9H04XjLXr2fbnHw-SLZga3PewEfgPz2s-mhp7NAz";
        HttpClientUtil.addRailCookies(RAIL_EXPIRATION,RAIL_DEVICEID);*/
        first5Conf();
        first6LoginBanner();
        first7UamtkStatic();
        secondGetCode();
        Scanner scan = new Scanner(System.in);
        System.out.print("输入整数：");
        String codeInput = "";
        while((scan.hasNextLine())){
            String code = scan.next();
            if("closed".equals(code)){
                break;
            }
            codeInput = thirdInputCode(code);//fourCheckCode(code);
            //{"result_message":"验证码校验成功","result_code":"4"}
            //result12306 = Login.fourthCheckCode(s,callBack,nums);
            fourthCheckCode(codeInput);
        }
        fifthWebLogin("xx","xx",codeInput);
    }

    /*
     * 第一步初始化登录
     * https://kyfw.12306.cn/otn/login/init
     * /otn/leftTicket/init
     */
    public static void firstInit(){
        logger.info("1：初始化登录");
        String initPath = "/otn/leftTicket/init";
        HttpGet httpGetInit = new HttpGet();
        httpGetInit.addHeader("User-Agent", USER_AGENT);
        httpGetInit.addHeader("Host", HOST);
        HttpClientUtil.httpRequest(initPath, httpGetInit);
    }
    /*
     * 第一步初始化登录
     * /passport/web/auth/uamtk-static
     * /otn/HttpZF/GetJS
     */
    public static String first2GetJs(){
        logger.info("2.3：初始化静态页面");
        String initPath = "/otn/HttpZF/GetJS";
        HttpGet httpGetInit = new HttpGet();
        httpGetInit.addHeader("User-Agent", USER_AGENT);
        httpGetInit.addHeader("Host", HOST);
        httpGetInit.addHeader("Referer", "https://kyfw.12306.cn/otn/resources/login.html");
        String jsStr = HttpClientUtil.httpRequest(initPath, httpGetInit);
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
    public static void first3LogDevice(String algID) throws Exception{
        logger.info("1.2：初始化登录");
        String logUrl = "https://kyfw.12306.cn/otn/HttpZF/logdevice";
        URIBuilder uriBuilder = new URIBuilder(logUrl);
        /*String logDevice = String.format("/otn/HttpZF/logdevice?"
                +"algID=%s&timestamp=%d%s", algID,
                System.currentTimeMillis(), first4GetLogDeviceParams());*/
        //{"validateMessagesShowId":"_validatorMessage","status":true,"httpstatus":200,"data":{"isstudentDate":false,"is_login_passCode":"Y","is_sweep_login":"Y","psr_qr_code_result":"N","login_url":"resources/login.html","studentDate":["2019-06-01","2019-09-30","2019-12-01","2019-12-31","2020-01-01","2020-03-31"],"stu_control":30,"is_uam_login":"Y","is_login":"N","other_control":30},"messages":[],"validateMessages":{}}
        uriBuilder.addParameter("algID",algID);
        first4GetLogDeviceParams(uriBuilder);
        uriBuilder.addParameter("timestamp",String.valueOf(System.currentTimeMillis()));
        HttpGet httpGetLog = new HttpGet();
        httpGetLog.addHeader("User-Agent", USER_AGENT);
        httpGetLog.addHeader("Host", HOST);
        httpGetLog.addHeader("X-Requested-With", "XMLHttpRequest");
        httpGetLog.addHeader("Referer", "https://kyfw.12306.cn/otn/resources/login.html");
        String timeValue = String.valueOf(System.currentTimeMillis());
        //httpGetLog.setURI(logDeviceUri("https://kyfw.12306.cn/otn/HttpZF/logdevice",timeValue));
        httpGetLog.setURI(uriBuilder.build());
        String logDevices = HttpClientUtil.httpRequest(null, httpGetLog);
        if(logDevices.contains("callbackFunction")){
            String str = logDevices.substring(logDevices.indexOf("{"),
                    logDevices.indexOf("}")+1);
            JSONObject obj = JSONObject.parseObject(str);
            HttpClientUtil.addRailCookies(obj.getString("exp"), obj.getString("dfp"));
        }
    }
    private static URI logDeviceUri(String url, String timeValue){
        URI uri;
        try{
            uri = new URIBuilder(url)
                    .setParameter("algID", "n92OblAvAq")
                    .setParameter("hashCode", "JS_FQ3BTVLvWMwH8qILnJjjT3w1Yy1yoRWlF6QkC6Vs")
                    .setParameter("FMQw", "0")
                    .setParameter("q4f3", "zh-CN")
                    .setParameter("VySQ", "FGHk8hqpvc5Q_Z7mhyp31spb7lnu9gYr")
                    .setParameter("VPIf", "1")
                    .setParameter("custID", "133")
                    .setParameter("VEek", "unknown")
                    .setParameter("dzuS", "0")
                    .setParameter("yD16", "0")
                    .setParameter("EOQP", "8f58b1186770646318a429cb33977d8c")
                    .setParameter("lEnu", "3232235976")
                    .setParameter("jp76", "52d67b2a5aa5e031084733d5006cc664")
                    .setParameter("hAqN", "Win32")
                    .setParameter("platform", "WEB")
                    .setParameter("ks0Q", "d22ca0b81584fbea62237b14bd04c866")
                    .setParameter("TeRS", "1040x1920")
                    .setParameter("tOHY", "24xx1080x1920")
                    .setParameter("Fvje", "i1l1o1s1")
                    .setParameter("q5aJ", "-2")
                    .setParameter("wNLf", "99115dfb07133750ba677d055874de87")
                    .setParameter("0aew", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36")
                    .setParameter("E3gR", "a0be7d173523aca243213e9d8aa434c9")
                    .setParameter("timestamp", timeValue)
                    .build();
            return uri;
        }catch (URISyntaxException e){
            return null;
        }
    }
    /**
     * 获取device参数
     * @return
     */
    public static void first4GetLogDeviceParams(URIBuilder uriBuilder){
        Random random = new Random();
        Map<String,String> dataMap = new LinkedHashMap();
        dataMap.put("adblock", "0");
        dataMap.put("browserLanguage", "en-US");
        dataMap.put("cookieEnabled", "1");
        dataMap.put("custID", "133");
        dataMap.put("doNotTrack", "unknown");
        dataMap.put("flashVersion", "0");
        dataMap.put("javaEnabled", "0");
        dataMap.put("jsFonts", "c227b88b01f5c513710d4b9f16a5ce52");
        dataMap.put("localCode", "3232236206");
        dataMap.put("mimeTypes", "52d67b2a5aa5e031084733d5006cc664");
        dataMap.put("os", "MacIntel");
        dataMap.put("platform", "WEB");
        dataMap.put("plugins", "d22ca0b81584fbea62237b14bd04c866");
        dataMap.put("scrAvailSize", (random.nextInt(1000) % (501) + 500) + "x1920");
        dataMap.put("srcScreenSize", "24xx1080x1920");
        dataMap.put("storeDb", "i1l1o1s1");
        dataMap.put("timeZone", "-8");
        dataMap.put("touchSupport", "99115dfb07133750ba677d055874de87");
        USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML); like Gecko) Chrome/75.0." +
                (random.nextInt(7000) % (2001) + 5000)+ ".0 Safari/537.36";
        dataMap.put("userAgent", USER_AGENT);
        dataMap.put("webSmartID", "f4e3b7b14cc647e30a6267028ad54c56");

        Map<String,String> dataTransMap = new HashMap<>();
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
        Set<Map.Entry<String, String>> entries = dataMap.entrySet();
        for(Map.Entry<String,String> entry:entries){
            String key = entry.getKey();
            String item = entry.getValue();
            d += (key + item);
            key = dataTransMap.get(key)!=null?dataTransMap.get(key):key;
            uriBuilder.addParameter(key,item);
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
        uriBuilder.addParameter("hashCode",dataStr);
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
        HttpPost httpPost = new HttpPost();
        httpPost.addHeader("Origin", "https://kyfw.12306.cn");
        httpPost.addHeader("User-Agent", USER_AGENT);
        httpPost.addHeader("Host", HOST);
        httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
        httpPost.addHeader("Referer", "https://kyfw.12306.cn/otn/resources/login.html");
        String confStr = HttpClientUtil.httpRequest(initPath, httpPost);
        logger.info("first5Conf:{}",confStr);
    }

    public static void first6LoginBanner(){
        logger.info("1：初始化登录");
        String initPath = "/otn/index12306/getLoginBanner";
        HttpGet httpPost = new HttpGet();
        httpPost.addHeader("User-Agent", USER_AGENT);
        httpPost.addHeader("Host", HOST);
        httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
        httpPost.addHeader("Referer", "https://kyfw.12306.cn/otn/resources/login.html");
        String confStr = HttpClientUtil.httpRequest(initPath, httpPost);
        logger.info("first6LoginBanner:{}",confStr);
    }
    public static void first7UamtkStatic(){
        logger.info("1：初始化登录");
        String initPath = "/passport/web/auth/uamtk-static";
        HttpPost httpPost = new HttpPost();
        httpPost.addHeader("User-Agent", USER_AGENT);
        httpPost.addHeader("Host", HOST);
        httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
        httpPost.addHeader("Origin", "https://kyfw.12306.cn");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.addHeader("Referer", "https://kyfw.12306.cn/otn/resources/login.html");
        //appid=otn
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("appid", "otn"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);
        httpPost.setEntity(entity);
        String confStr = HttpClientUtil.httpRequest(initPath, httpPost);
        logger.info("first7UamtkStatic:{}",confStr);
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
        /*String codePath = String.format("/passport/captcha/captcha-image64?" +
                "login_site=E&module=login&rand=sjrand&%d" +
                "&callback=jQuery191025909781158866285_1577623706238&_=%d",
                System.currentTimeMillis(),System.currentTimeMillis());*/
        String timeValue = String.valueOf(System.currentTimeMillis());
        String paramsCallback = getCheckCode() + "_" + timeValue;
        HttpGet httpGetCode = new HttpGet();
        httpGetCode.addHeader("User-Agent", USER_AGENT);
        httpGetCode.addHeader("Host", HOST);
        httpGetCode.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpGetCode.setURI(doUri(timeValue,paramsCallback));
        HttpClientUtil.httpRequestImage(null, httpGetCode,"D:\\WorkSpace\\result\\20191221\\random.png");
        //HttpClientUtil.httpRequest(codePath,httpGet);
    }
    private static String getCheckCode(){
        Random random = new Random();
        String checkCode = "jQuery1910";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<16; i++){
            stringBuilder.append(random.nextInt(9));
        }
        checkCode += stringBuilder;
        return checkCode;
    }
    private static URI doUri(String timeValue, String paramsCallback){
        URI uri;
        try{
            uri = new URIBuilder("https://kyfw.12306.cn/passport/captcha/captcha-image64")
                    .setParameter("login_site", "E")
                    .setParameter("module", "login")
                    .setParameter("rand", "sjrand")
                    .setParameter(String.valueOf(Long.valueOf(timeValue) + 100000L), "")
                    .setParameter("callback", paramsCallback)
                    .setParameter("_ ", timeValue)
                    .build();
            return uri;
        }catch (URISyntaxException e){
            return null;
        }
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
        HttpGet httpGetCode = new HttpGet();
        httpGetCode.addHeader("User-Agent", USER_AGENT);
        httpGetCode.addHeader("Host", HOST);
        httpGetCode.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        String response = HttpClientUtil.httpRequest(checkCodePath, httpGetCode);
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
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", name));
        params.add(new BasicNameValuePair("password", pwd));
        params.add(new BasicNameValuePair("appid", "otn"));
        params.add(new BasicNameValuePair("answer", code));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);
        HttpPost httpPost = new HttpPost();
        httpPost.addHeader("User-Agent", USER_AGENT);
        httpPost.addHeader("Host", HOST);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setEntity(entity);
        String response=  HttpClientUtil.httpRequest(loginPath,httpPost);
        //{"result_message":"登录成功","result_code":0,"uamtk":"yYyKPBEQh8re5T4otBKA5Mj7GCOF1R5cl65R9Qafh2h0"}
        logger.info("login:{}",response);
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
