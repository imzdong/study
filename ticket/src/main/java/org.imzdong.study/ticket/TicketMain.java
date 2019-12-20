package org.imzdong.study.ticket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.imzdong.study.ticket.dto.PassengerDTO;
import org.imzdong.study.ticket.dto.TicketInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @description:
 * @author: Winter
 * @time: 2019/12/18
*/

public class TicketMain {
    private final static Logger log = LoggerFactory.getLogger(TicketMain.class);
    //private static HttpClient build;
    private static CloseableHttpClient httpclient = HttpClients.createDefault();
    private static String GET = "GET";
    private static String POST = "POST";
    private static String params = null;
    private static String type = null;
    //private static String imagePath = "D:\\workspace\\result\\random.png";
    private static String imagePath = "D:\\WorkSpace\\result\\random.png";
    private static CookieManager cm = new CookieManager();



/*
     * 1、https://kyfw.12306.cn/otn/leftTicket/init   （left_ticket_init）
     * 2、https://kyfw.12306.cn/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&{0}  （getCodeImg）
     * 2.1、https://kyfw.12306.cn/passport/web/auth/uamtk   "appid": "otn"  （auth） 登录认证接口
     * 3、https://kyfw.12306.cn/passport/captcha/captcha-check （codeCheck）
     *
     * 3、https://kyfw.12306.cn/passport/web/login
     * {
     *  "username": user,
     *  "password": passwd,
     *  "appid": "otn"
     * }   登录成功
*/

    public static void main(String[] args)throws Exception{
        String userName = "12306账号";
        String password = "12306密码";
        String seatName = "硬卧";
        String[] ticketPass = {"乘客01","乘客02"};
        String from = "BJP";
        String to = "NFF";
        String trainDate = "2019-02-22";
        String trainCode = "K279";
        log.info("start:{}",new Date());
        cm.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cm);
        ProxySelector proxySelector = new ProxySelector() {
            @Override
            public List<Proxy> select(URI uri) {
                List<Proxy> list = new ArrayList();
                // 代理地址
                String address = "127.0.0.1";
                // 代理端口
                int port = 800123;
                InetSocketAddress sockAddr = new InetSocketAddress(address, port);
                // Proxy.Type.SOCKS Socks代理类型
                Proxy proxy = new Proxy(Proxy.Type.HTTP, sockAddr);
                list.add(proxy);
                return list;
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                log.info("代理失败!");
            }
        };
        /*build = HttpClient.newBuilder()
                .cookieHandler(CookieHandler.getDefault())
                //.proxy(proxySelector)
                .build();*/
        log.info("1、初始化登陆：/otn/leftTicket/init");
        oneInitLogin();
        log.info("2、获取验证码：/passport/captcha/captcha-image");
        secondGetCode();
        //secondOAuth();
        Scanner scan = new Scanner(System.in);
        System.out.print("输入整数：");
        String code = "";
        String result12306 = null;
        String umktk = null;
        while((scan.hasNextLine()&&!"4".equals(result12306))){
            code = scan.next();
            if("closed".equals(code)){
                break;
            }
            String s = resultToCode(code);//fourCheckCode(code);
            //{"result_message":"验证码校验成功","result_code":"4"}
            log.info("3、校验验证码：/passport/captcha/captcha-check");
            result12306 = fourOCheckCode(s);
        }
        log.info("4、12306用户账号登陆：/passport/web/login");
        umktk = fiveWebLogin(userName,password);
        log.info("result12306：{}",result12306);
        log.info("umktk：{}",umktk);
        if("4".equals(result12306)&&StringUtils.isNotBlank(umktk)){
            log.info("5、12306获取用户apptk：/passport/web/auth/uamtk");
            String newAppTk = secondOAuth();//newapptk
            //String umk = umktk;
            log.info("newapptk：{}",newAppTk);
            if(newAppTk!=null){
                log.info("6、12306获取用户cookie：otn/uamauthclient");
                getUser(newAppTk);
                //checkUser();//校验是否登录
                log.info("7、查询余票：leftTicket/query");
                List<TicketInfoDTO> ticketInfoDTOS = queryTicket(trainDate,from,to);
                log.info("ticketInfoDTOS：{}",ticketInfoDTOS);
                log.info("ticketInfoDTOS size：{}",ticketInfoDTOS.size());
                ticketInfoDTOS = transTicket(ticketInfoDTOS);
                log.info("ticketInfoDTOS 有票 size：{}",ticketInfoDTOS.size());
                log.info("ticketInfoDTOS 有票：{}",ticketInfoDTOS);
                TicketInfoDTO resultDto = null;
                for(TicketInfoDTO ticketInfoDTO:ticketInfoDTOS){
                    if(trainCode.equals(ticketInfoDTO.getStationTrainCode())&&
                            seatName.equals(ticketInfoDTO.getSeatName())){
                        resultDto = ticketInfoDTO;
                        break;
                    }
                }
                log.info("符合目标的车次：{}",resultDto);
                if(resultDto!=null){
                    log.info("8、点击预定：/otn/leftTicket/submitOrderRequest");
                    boolean reserveSubmitOrder = submitOrder(ticketInfoDTOS.get(0).getSecretStr(),
                            trainDate,
                            from, to);
                    if(reserveSubmitOrder){
                        log.info("9、进入预定单程页面：/otn/confirmPassenger/initDc");
                        JSONObject initDcSubmitToken = getSubmitToken();
                        if(initDcSubmitToken!=null) {
                            //获取订单提交token
                            String globalRepeatSubmitToken = initDcSubmitToken.getString("globalRepeatSubmitToken");
                            log.info("10、获取乘客信息：/otn/confirmPassenger/getPassengerDTOs");
                            List<PassengerDTO> passenger = getPassenger(globalRepeatSubmitToken);
                            if(passenger.size()>0) {
                                JSONObject passengerJson = tranPassenger(passenger, seatName, ticketPass);
                                log.info("符合目标的乘客数据：{}", passengerJson);
                                log.info("11、确认订单信息：/otn/confirmPassenger/checkOrderInfo");
                                String checkOrder = checkOrder(globalRepeatSubmitToken
                                        , passengerJson.getString("passengerTicketStr")
                                        , passengerJson.getString("oldPassengerStr"));
                                JSONObject checkSubmitJson = JSONObject.parseObject(checkOrder);
                                if(checkSubmitJson.get("data")!=null&&
                                        checkSubmitJson.getJSONObject("data").getBoolean("submitStatus"))
                                {
                                    log.info("车票提交通过，正在尝试排队");
                                    JSONObject dataSubmit = checkSubmitJson.getJSONObject("data");
                                    int ifShowPassCodeTime = dataSubmit.getIntValue("ifShowPassCodeTime");//验证码展示时间
                                    String ifShowPassCode = dataSubmit.getString("ifShowPassCode");//是否需要提交验证码
                                    boolean submitStatus = dataSubmit.getBoolean("submitStatus");//提交状态
                                    boolean isNeedCode = false;
                                    if(ifShowPassCode!=null&&"Y".equals(ifShowPassCode)){
                                        isNeedCode = true;
                                    }
                                    log.info("提交订单是否需要验证码：ifShowPassCodeTime:{}," +
                                                    "ifShowPassCode:{},isNeedCode:{},submitStatus:{}",
                                            ifShowPassCodeTime,ifShowPassCode,isNeedCode,submitStatus);
                                    log.info("12、获取排队信息：/otn/confirmPassenger/getQueueCount");
                                    String queueCount = getQueueCount(initDcSubmitToken, seatNo.get(seatName), trainDate);
                                    JSONObject queueCountJson = JSONObject.parseObject(queueCount);
                                    if(queueCountJson.getBoolean("status")){
                                        JSONObject queueData = queueCountJson.getJSONObject("data");
                                        String countT = queueData.getString("countT");//剩余几张票
                                        String ticket = queueData.getString("ticket");//排在第几行
                                        log.info("排队成功, 你排在: {}位, 当前余票还剩余: {} 张",
                                                ticket,countT);
                                        if(StringUtils.isNotBlank(countT)){
                                            if(isNeedCode){//需要验证码
                                                log.info("需要验证码！");
                                            }else{
                                                log.info("13、进入队伍,确认下单：/otn/confirmPassenger/confirmSingleForQueue");
                                                String confirmSingleForQueue = confirmSingleForQueue(initDcSubmitToken, passengerJson.getString("passengerTicketStr")
                                                        , passengerJson.getString("oldPassengerStr")
                                                        , "");
                                                JSONObject confirmQueueJson = JSONObject.parseObject(confirmSingleForQueue);
                                                if(confirmQueueJson.getBoolean("status")&&confirmQueueJson.getJSONObject("data")!=null
                                                        &&confirmQueueJson.getJSONObject("data").getBoolean("submitStatus")){
                                                    log.info("14、下单成功，查询订单号：/otn/confirmPassenger/queryOrderWaitTime");
                                                    String orderId = queryOrderWaitTime(globalRepeatSubmitToken);
                                                    if(orderId!=null){
                                                        log.info("15、下单成功，确认订单状态：/otn/confirmPassenger/resultOrderForDcQueue");
                                                        boolean resultOrderForDcQueue = resultOrderForDcQueue(globalRepeatSubmitToken, orderId);
                                                        log.info("16、下单成功，获取订单详情：/otn/queryOrder/queryMyOrderNoComplete");
                                                        queryMyOrderNoComplete();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        scan.close();
        log.info("end:{}",new Date());
    }



/*
     * 第七步检查用户是否登录
     * @return
 */

    private static String checkUser(){
        String checkUserUrl = host + "/otn/login/checkUser";
        //data = {"_json_att": ""}
        String body = String.format("_json_att=%s"
                ,"");
        String response = httpUtil(checkUserUrl,body,POST,type);
        log.info("第七步检查用户是否登录：{}",response);
        return response;
    }

/*
     *  第八步查询余票
     * @return
     * @throws Exception
 */




/*
     * 获取乘客信息
     * @param token
     * @return
 */




/*
     * 检查支付订单，需要提交REPEAT_SUBMIT_TOKEN
     *         passengerTicketStr : 座位编号,0,票类型,乘客名,证件类型,证件号,手机号码,保存常用联系人(Y或N)
     *         oldPassengersStr: 乘客名,证件类型,证件号,乘客类型
     * @param token
     * @return
 */

    private static String checkOrder(String token,String passengerTicketStr
            ,String oldPassengerStr) {
        String checkOrderUrl = host + "/otn/confirmPassenger/checkOrderInfo";
        //座位编号,0,票类型,乘客名,证件类型,证件号,手机号码,保存常用联系人(Y或N)
        //String passengerTicketStr = "2019-01-28";
        //String oldPassengerStr = ""; //乘客名,证件类型,证件号,乘客类型
        String randCode = "";//
        String cancel_flag = "2";
        String bed_level_order_num = "000000000000000000000000000000";
        String tour_flag = "dc";
        String _json_att = "";
        String body = String.format("passengerTicketStr=%s" +
                        "&oldPassengerStr=%s" +
                        "&REPEAT_SUBMIT_TOKEN=%s" +
                        "&randCode=%s" +
                        "&cancel_flag=%s" +
                        "&bed_level_order_num=%s" +
                        "&tour_flag=%s" +
                        "&_json_att=%s",
                passengerTicketStr,
                oldPassengerStr,
                token,
                randCode,
                cancel_flag,
                bed_level_order_num,
                tour_flag,
                _json_att);
        String response = httpUtil(checkOrderUrl,body,POST,type);
        log.info("第11步检查订单：{}",response);
        //{"validateMessagesShowId":"_validatorMessage","status":true,"httpstatus":200,
        // "data":{"errMsg":"系统繁忙，请稍后重试！","submitStatus":false},"messages":[],"validateMessages":{}}
        return response;
    }

/*
     * 模拟查询当前的列车排队人数的方法
     * 剩余余票数
     * @param submitToken
     * @param seatType
     * @return
 */

    private static String getQueueCount(JSONObject submitToken
            ,String seatType,String trainDate) throws Exception{
        String getQueueCount = host + "/otn/confirmPassenger/getQueueCount";
        JSONObject ticketInfoForPassengerForm = submitToken.getJSONObject("ticketInfoForPassengerForm");
        JSONObject queryLeftTicketRequestDTO = ticketInfoForPassengerForm.getJSONObject("queryLeftTicketRequestDTO");
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE+MMM+dd+yyyy+HH:mm:ss+'GMT+0800+(中国标准时间)'", Locale.US);
        String train_date = sdf.format(ft.parse(trainDate));
        //String train_date = getGMT(trainDate);
        String train_no = queryLeftTicketRequestDTO.getString("train_no");
        String stationTrainCode = queryLeftTicketRequestDTO.getString("station_train_code");
        String fromStationTelecode = queryLeftTicketRequestDTO.getString("from_station");
        String toStationTelecode = queryLeftTicketRequestDTO.getString("to_station");
        String leftTicket = ticketInfoForPassengerForm.getString("leftTicketStr");
        String purpose_codes = ticketInfoForPassengerForm.getString("purpose_codes");
        String train_location = ticketInfoForPassengerForm.getString("train_location");
        String body = String.format("train_date=%s" +
                        "&train_no=%s" +
                        "&stationTrainCode=%s" +
                        "&seatType=%s" +
                        "&fromStationTelecode=%s" +
                        "&toStationTelecode=%s" +
                        "&leftTicket=%s" +
                        "&purpose_codes=%s"+
                        "&train_location=%s"+
                        "&_json_att=%s"+
                        "&REPEAT_SUBMIT_TOKEN=%s",
                train_date,
                train_no,
                stationTrainCode,
                seatType,
                fromStationTelecode,
                toStationTelecode,
                leftTicket,
                purpose_codes,
                train_location,
                "",
                submitToken.getString("globalRepeatSubmitToken")
        );
        log.info("第12步模拟查询当前的列车参数:{}",body);
        boolean status = false;
        int num = 0;
        String result = "{\"validateMessagesShowId\": \"_validatorMessage\",\"data\": {\"countT\": \"10\",\"ticket\": \"888\"},\"status\": true,\"httpstatus\": 200,\"messages\": [\"success\"],\"validateMessages\": {}}";
        //reqdata_Str= bytes.decode(urllib.parse.urlencode(reqdata).encode('utf-8'))
        //.replace('%2B','+').replace('GMT+0800','GMT%2B0800').replace('%28','(').replace('%29',')')
        //body = body.getBytes("UTF-8")
        //body = URLDecoder.decode(URLEncoder.encode(body, "UTF-8"),"UTF-8");
        //编码
byte[] bytes = body.getBytes("UTF-8");
        String decodeStr = new String(bytes);
        body = decodeStr.replaceAll("%2B","+").replaceAll("GMT+0800","GMT%2B0800")
                .replaceAll("%28","(").replaceAll("%29",")");

        //end
        while(num<5||status) {
            num++;
            Thread.sleep(3000L);
            String response = httpUtil(getQueueCount, body, "POST", null);
            log.info("第12步模拟查询当前的列车排队人数的方法：{}",response);
            try{
                if(StringUtils.isNotBlank(response)){
                    JSONObject queueJson = JSONObject.parseObject(response);
                    status = queueJson.getBoolean("status");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            result = response;
        }
        if(!status){
            result = "{\"validateMessagesShowId\": \"_validatorMessage\",\"data\": {\"countT\": \"10\",\"ticket\": \"888\"},\"status\": true,\"httpstatus\": 200,\"messages\": [\"success\"],\"validateMessages\": {}}";
        }
        //{"validateMessagesShowId":"_validatorMessage","url":"/leftTicket/init","status":false,"httpstatus":200,
        // "messages":["系统忙，请稍后重试"],"validateMessages":{}}
        //貌似这步失败了下面的也可以成功
        return result;
    }

    public static String getGMT(String date){
        String str="";
        TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
        TimeZone.setDefault(tz);
        Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
        Date dd;
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            dd = shortSdf.parse(date);
            cal.setTime(dd);
            str = sdf.format(cal.getTime());
            return str+"+0800 (中国标准时间)";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

/*
     * 确认订单
     * @param submitToken
     * @param passengerTicketStr
     * @param oldPassengerStr
     * @param randCode
     * @return
     * @throws Exception
 */

    private static String confirmSingleForQueue(JSONObject submitToken
            ,String passengerTicketStr,String oldPassengerStr,String randCode) throws Exception{
        String confirmQueue = host + "/otn/confirmPassenger/confirmSingleForQueue";
        JSONObject ticketInfoForPassengerForm = submitToken.getJSONObject("ticketInfoForPassengerForm");
        String purpose_codes = ticketInfoForPassengerForm.getString("purpose_codes");
        String key_check_isChange = ticketInfoForPassengerForm.getString("key_check_isChange");
        String leftTicketStr = ticketInfoForPassengerForm.getString("leftTicketStr");
        String train_location = ticketInfoForPassengerForm.getString("train_location");
        String body = String.format("passengerTicketStr=%s" +
                        "&oldPassengerStr=%s" +
                        "&purpose_codes=%s" +
                        "&key_check_isChange=%s" +
                        "&leftTicketStr=%s" +
                        "&train_location=%s" +
                        "&seatDetailType=%s" +
                        "&roomType=%s"+
                        "&dwAll=%s"+
                        "&whatsSelect=%s"+
                        "&_json_at=%s"+
                        "&randCode=%s"+
                        "&choose_seats=%s"+
                        "&REPEAT_SUBMIT_TOKEN=%s",
                passengerTicketStr,
                oldPassengerStr,
                purpose_codes,
                key_check_isChange,
                leftTicketStr,
                train_location,
                "",
                "00",
                "N",
                "1",
                "",
                randCode,
                "",
                submitToken.getString("globalRepeatSubmitToken"));
        String response = httpUtil(confirmQueue,body,POST,type);
        log.info("第13步确认队列提交订单：{}",response);
        return response;
    }

/*
     * 确认订单状态
     * @param globalToken
     * @return
 */

    private static String queryOrderWaitTime(String globalToken) throws Exception{
        String checkUserUrl = host + "/otn/confirmPassenger/queryOrderWaitTime";
        String orderId = null;
        //data = {"_json_att": ""}
        int num = 0;
        while(orderId==null||num<50) {
            num++;
            String body = String.format("_json_att=%s" +
                            "&REPEAT_SUBMIT_TOKEN=%s" +
                            "&tourFlag=%s" +
                            "&random=%s"
                    ,""
                    ,globalToken
                    ,"dc"
                    ,System.currentTimeMillis());
            String response = httpUtil(checkUserUrl,body,GET,type);
            log.info("第14步确认订单状态：{}",response);
            Thread.sleep(3000L);
            JSONObject queryOrderResult = JSONObject.parseObject(response);
            if(queryOrderResult.getBoolean("status")&&queryOrderResult.getJSONObject("data")!=null
                    && StringUtils.isNotBlank(queryOrderResult.getJSONObject("data").getString("orderId"))){
                orderId = queryOrderResult.getJSONObject("data").getString("orderId");
            }
        }
        return orderId;
    }

/*
     * 确认真实订单状态
     * @param globalToken
     * @param orderId
     * @return
     * @throws Exception
 */

    private static boolean resultOrderForDcQueue(String globalToken,String orderId) throws Exception{
        String checkUserUrl = host + "/otn/confirmPassenger/resultOrderForDcQueue";
        String body = String.format("_json_att=%s" +
                        "&REPEAT_SUBMIT_TOKEN=%s" +
                        "&orderSequence_no=%s"
                ,""
                ,globalToken
                ,orderId);
        String response = httpUtil(checkUserUrl,body,POST,type);
        log.info("第15步获取真正订单状态：{}",response);
        JSONObject queryOrderResult = JSONObject.parseObject(response);
        if(queryOrderResult.getBoolean("status")&&queryOrderResult.getJSONObject("data")!=null
                &&queryOrderResult.getJSONObject("data").getBoolean("submitStatus")){
            return true;
        }
        return false;
    }

/*
     * 获取订单详情
     * @return
     * @throws Exception
 */

    private static String queryMyOrderNoComplete() throws Exception{
        String checkUserUrl = host + "/otn/queryOrder/queryMyOrderNoComplete";
        String body = String.format("_json_att=%s"
                ,"");
        String response = httpUtil(checkUserUrl,body,POST,type);
        log.info("第16步获取订单详情：{}",response);
        return response;
    }


    private static String httpUtil(String httpUrl
            , String body, HttpRequest httpRequest, String type){
        return "";
    }


}
