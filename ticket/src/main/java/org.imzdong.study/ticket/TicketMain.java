package org.imzdong.study.ticket;

import sun.net.www.http.HttpClient;

import java.lang.reflect.Proxy;
import java.net.*;
import java.util.*;

/**
 * @description:
 * @author: Winter
 * @time: 2019/12/18
 */
public class TicketMain {
    private static String host = "https://kyfw.12306.cn";
    private static HttpClient build;
    private static String GET = "GET";
    private static String POST = "POST";
    private static String params = null;
    private static String type = null;
    //private static String imagePath = "D:\\workspace\\result\\random.png";
    private static String imagePath = "D:\\WorkSpace\\result\\random.png";
    private static CookieManager cm = new CookieManager();
    private static Map<String,String> seatMap = new HashMap<>();
    private static Map<String,String> seatNo = new HashMap<>();

    static{
        seatMap.put("商务座","32");
        seatMap.put("一等座","31");
        seatMap.put("二等座","30");
        seatMap.put("特等座","25");
        seatMap.put("软卧","23");
        seatMap.put("硬卧","28");
        seatMap.put("硬座","29");
        seatMap.put("无座","26");
        seatMap.put("动卧","33");
        seatNo.put("一等座","M");
        seatNo.put("特等座","P");
        seatNo.put("二等座","O");
        seatNo.put("商务座","9");
        seatNo.put("硬座","1");
        seatNo.put("无座","1");
        seatNo.put("软座","2");
        seatNo.put("软卧","4");
        seatNo.put("硬卧","3");
        seatNo.put("动卧","1");
    }

    /**
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
        build = HttpClient.newBuilder()
                .cookieHandler(CookieHandler.getDefault())
                //.proxy(proxySelector)
                .build();
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
                                JSONObject checkSubmitJson = new JSONObject(checkOrder);
                                if(checkSubmitJson.get("data")!=null&&
                                        checkSubmitJson.getJSONObject("data").getBoolean("submitStatus"))
                                {
                                    log.info("车票提交通过，正在尝试排队");
                                    JSONObject dataSubmit = checkSubmitJson.getJSONObject("data");
                                    int ifShowPassCodeTime = dataSubmit.getInt("ifShowPassCodeTime");//验证码展示时间
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
                                    JSONObject queueCountJson = new JSONObject(queueCount);
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
                                                JSONObject confirmQueueJson = new JSONObject(confirmSingleForQueue);
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

    /**
     *
     * {"passengerTicketStr":"3,0,1,乘客01,1,身份证xxx01,,N_3,0,1,乘客02,1,身份证xxx02,,N",
     * "oldPassengerStr":"乘客01,1,身份证xxx01,1_乘客02,1,身份证xxx02,1"}
     *
     * passengerTicketStr	O,0,1,文贤平,1,43052419950223XXXX,15618715583,N_O,0,1,梁敏,1,43052719920118XXXX,,N
     * oldPassengerStr	文贤平,1,43052719920118XXXX,1_梁敏,1,43052719920118XXXX,1
     * @return
     */
    public static JSONObject tranPassenger(List<PassengerDTO> passenger,
                                           String seatName,String[] ticketPass) throws Exception{
        String seatType = seatNo.get(seatName);
        String passengerTicketStr = seatType+",";
        String oldPassengerStr = "";
        for(PassengerDTO dto:passenger){
            String passenger_name = dto.getPassenger_name();
            for(String ticket:ticketPass){
                if(passenger_name.equals(ticket)){
                    passengerTicketStr += ("0,"+dto.getPassenger_type()
                            +","+dto.getPassenger_name()+","+dto.getPassenger_id_type_code()
                            +","+dto.getPassenger_id_no()+","+dto.getMobile_no()
                            +",N_"+seatType+",");
                    oldPassengerStr += (dto.getPassenger_name()+","
                            +dto.getPassenger_id_type_code()+","
                            +dto.getPassenger_id_no()+","+dto.getPassenger_type()+"_");
                }
            }
        }
        JSONObject passJson = new JSONObject();
        passengerTicketStr = passengerTicketStr.substring(0,passengerTicketStr.length()-3);
        oldPassengerStr = oldPassengerStr.substring(0,oldPassengerStr.length()-1);
        log.info("passengerTicketStr:{}",passengerTicketStr);
        log.info("oldPassengerStr:{}",oldPassengerStr);
        passJson.put("passengerTicketStr",passengerTicketStr);
        passJson.put("oldPassengerStr",oldPassengerStr);
        return passJson;
    }

    /**
     * 第一步初始化登录
     */
    private static void oneInitLogin(){
        String loginInitUrl = host + "/otn/leftTicket/init";
        //loginInitUrl = "https://www.baidu.com";
        String response= httpUtil(loginInitUrl,params,GET,type);
        log.info("第一步：初始化登录：statusCode:{}",response);
        //,response.body());
    }
    /**
     * 第二步
     * 下载验证码
     * 获取登录验证码存储本地
     */
    private static void secondGetCode(){
        Random random = new Random();//默认构造方法
        String randomNum = random.nextInt(1000000)+"";
        String getLoginCodeUrl = host + "/passport/captcha/captcha-image?" +
                "login_site=E&module=login&rand=sjrand&"+randomNum;
        String response= httpUtil(getLoginCodeUrl,params,GET,"PNG");
        log.info("第二步：下载验证码：statusCode：{}",response);
    }

    /**
     * 第2.1步认证
     */
    private static String secondOAuth(){
        String authUrl = host + "/passport/web/auth/uamtk";
        String body = "appid=otn";
        String response=  httpUtil(authUrl,body,POST,type);
        log.info("第2.1步：认证：{}",response);
        String umk=null;
        if(response.contains("newapptk")){
            JSONObject authJson = new JSONObject(response);
            umk = authJson.getString("newapptk");
        }
        return umk;
    }

    private static String fourOCheckCode(String code) {
        String checkCodeUrl = host + "/passport/captcha/captcha-check";
        String body = "rand=sjrand&login_site=E&answer="+code;
        String response=  httpUtil(checkCodeUrl,body,POST,type);
        log.info("第四步：转换12306验证码{}验证body:{}",code,response);
        if(response.contains("result_code")){
            JSONObject code12306 = new JSONObject(response);
            return code12306.getString("result_code");
        }
        return response;
    }

    /**
     * 第五步初始化登录
     */
    private static String fiveWebLogin(String name,String pwd) {
        String loginWebUrl = host + "/passport/web/login";
        String body = String.format("username=%s&password=%s&appid=otn"
                ,name,pwd);
        String response=  httpUtil(loginWebUrl,body,POST,type);
        log.info("第五步登录：{}",response);
        //{"result_message":"登录成功","result_code":0,"uamtk":"yYyKPBEQh8re5T4otBKA5Mj7GCOF1R5cl65R9Qafh2h0"}
        JSONObject loginJson = new JSONObject(response);
        if(StringUtils.isNotBlank(loginJson.getString("uamtk"))){
            return loginJson.getString("uamtk");
        }
        return null;
    }

    /**
     * 第六步获取用户名
     * @param uamtk
     * @return
     */
    private static String getUser(String uamtk){
        String getUserUrl = host + "/otn/uamauthclient";
        //{"tk": uamtk}
        String body = String.format("tk=%s"
                ,uamtk);
        String response=  httpUtil(getUserUrl,body,POST,type);
        log.info("第六步获取用户名：{}",response);
        return response;
    }

    /**
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

    /**
     *  第八步查询余票
     * @return
     * @throws Exception
     */
    private static List<TicketInfoDTO> queryTicket(String ticketDate,
                                                   String from_station, String to_station) throws Exception{
        ///otn/leftTicket/queryZ?leftTicketDTO.train_date={0}&leftTicketDTO.from_station={1}&leftTicketDTO.to_station={2}&purpose_codes=ADULT
        //String ticketDate = "2019-01-28";
        //String from_station = "BJP";
        //String to_station = "NFF";
        String result = "";
        boolean queryUrlFlag = true;
        while (queryUrlFlag){
            String realQuery = "leftTicket/queryA";
            if(result.contains("c_url")){
                realQuery = new JSONObject(result).getString("c_url");
                queryUrlFlag = false;
            }
            Thread.sleep(1000L);
            String queryUrl = "/otn/"+realQuery+"?";
            String queryTicketUrl = host + String.format(queryUrl +
                            "leftTicketDTO.train_date=%s" +
                            "&leftTicketDTO.from_station=%s" +
                            "&leftTicketDTO.to_station=%s&purpose_codes=ADULT"
                    ,ticketDate,from_station,to_station);
            String response = httpUtil(queryTicketUrl,null,"GET",null);
            log.info("第八步查票：{}---{}",queryTicketUrl,response);
            result = response;
        }
        JSONObject ticketJson = new JSONObject(result);
        JSONObject resultT = ticketJson.getJSONObject("data");
        JSONArray resultArray = resultT.getJSONArray("result");
        List<TicketInfoDTO> listTicket = new ArrayList<>();
        Set<String> seatIndexs = seatMap.keySet();
        for(int numT=0;numT<resultArray.length();numT++){
            String r0 = resultArray.get(numT).toString();
            String[] ticketArray = r0.split("\\|");
            for(String seatName:seatIndexs){
                String seatIndex = seatMap.get(seatName);
                String ticketNum = ticketArray[Integer.parseInt(seatIndex)];
                String isLeftTicket = ticketArray[11];//是否有票 Y
                String reserve = URLDecoder.decode(ticketArray[1], "UTF-8");//预定
                TicketInfoDTO ticketInfoDTO = new TicketInfoDTO();
                ticketInfoDTO.setSecretStr(ticketArray[0]);//加密串
                ticketInfoDTO.setTrain_no(ticketArray[2]);//240000K58916
                ticketInfoDTO.setQuery_from_station_name(ticketArray[6]);//初始站
                ticketInfoDTO.setQuery_to_station_name(ticketArray[7]);//抵达站
                ticketInfoDTO.setTrain_location(ticketArray[15]);//PB
                ticketInfoDTO.setStationTrainCode(ticketArray[3]);//车次号
                ticketInfoDTO.setLeftTicket(ticketArray[12]);
                ticketInfoDTO.setStart_time(ticketArray[8]);
                ticketInfoDTO.setArrival_time(ticketArray[9]);
                ticketInfoDTO.setDistance_time(ticketArray[10]);
                ticketInfoDTO.setTicket_num(ticketNum);
                ticketInfoDTO.setIsLeftTicket(isLeftTicket);
                ticketInfoDTO.setReserve(reserve);
                ticketInfoDTO.setSeatName(seatName);
                listTicket.add(ticketInfoDTO);
            }
        }
        return transTicket(listTicket);
    }

    private static List<TicketInfoDTO> transTicket(List<TicketInfoDTO> listTicket) throws Exception{
        List<TicketInfoDTO> listTransTicket = new ArrayList<>();
        for(TicketInfoDTO tto:listTicket){
            //is_ticket_pass != '' and is_ticket_pass != '无' and is_ticket_pass != '*'
            if("预订".equals(tto.getReserve())&&"Y".equals(tto.getIsLeftTicket())
                    &&StringUtils.isNotBlank(tto.getTicket_num())&&!"无".equals(tto.getTicket_num())
                    &&!"*".equals(tto.getTicket_num())){
                listTransTicket.add(tto);
            }
        }
        log.info("有票：{}",listTransTicket.toString());
        return listTransTicket;
    }

    /**
     * 获取乘客信息
     * @param token
     * @return
     */
    private static List<PassengerDTO> getPassenger(String token) {
        String getPassengerUrl = host + "/otn/confirmPassenger/getPassengerDTOs";
        String body = String.format("REPEAT_SUBMIT_TOKEN=%s",token);
        String response = httpUtil(getPassengerUrl,body,POST,type);
        log.info("第九步获取乘客：{}",response);
        JSONObject passengerJson = new JSONObject(response);
        boolean status = passengerJson.getBoolean("status");
        List<PassengerDTO> passList = new ArrayList<>();
        if(status){
            JSONObject data = passengerJson.getJSONObject("data");
            if(data!=null&&data.getBoolean("isExist")){
                JSONArray normal_passengers = data.getJSONArray("normal_passengers");
                for(int num=0;num<normal_passengers.length();num++){
                    JSONObject passJson = normal_passengers.getJSONObject(num);
                    PassengerDTO passengerDTO = new PassengerDTO();
                    passengerDTO.setMobile_no(passJson.getString("mobile_no"));
                    passengerDTO.setPassenger_id_no(passJson.getString("passenger_id_no"));
                    passengerDTO.setPassenger_id_type_code(passJson.getString("passenger_id_type_code"));
                    passengerDTO.setPassenger_name(passJson.getString("passenger_name"));
                    passengerDTO.setPassenger_type(passJson.getString("passenger_type"));
                    passList.add(passengerDTO);
                }
            }

        }
        return passList;
    }

    /**
     * 提交订单
     * @param secretStr
     * @return
     */
    private static boolean submitOrder(String secretStr,String train_date,
                                       String query_from_station_name, String query_to_station_name) {
        String submitOrderUrl = host + "/otn/leftTicket/submitOrderRequest";
        //String seatType = seatNo.get(seatName);
        //String secretStr = ""; //字符串加密
        //String train_date = "2019-01-28";//出发时间
        String back_train_date = ""; //返程时间
        String tour_flag = "dc";//旅途类型
        String purpose_codes = "ADULT";//成人票还是学生票
        //String query_from_station_name = "BJP";//起始车站
        //String query_to_station_name = "NFF";//终点车站
        String body = String.format("secretStr=%s&train_date=%s&back_train_date=%s&tour_flag=%s" +
                        "&purpose_codes=%s&query_from_station_name=%s&query_to_station_name=%s",
                secretStr,train_date,back_train_date
                ,tour_flag,purpose_codes,query_from_station_name,query_to_station_name);
        String response = httpUtil(submitOrderUrl,body,POST,type);
        log.info("第10步提交订单：{}",response);
        //{"validateMessagesShowId":"_validatorMessage",
        // "status":true,"httpstatus":200,"data":"N","messages":[],"validateMessages":{}}
        JSONObject submitJson = new JSONObject(response);
        if("N".equals(submitJson.getString("data"))){
            return true;
        }else{
            return false;
        }
    }

    private static JSONObject getSubmitToken() throws Exception{
        String getSubmitTokenUrl = host + "/otn/confirmPassenger/initDc";
        String response = httpUtil(getSubmitTokenUrl,params,GET,type);
        log.info("第10.1步获取提交订单Token：{}",response);
        //{"validateMessagesShowId":"_validatorMessage","status":true,"httpstatus":200,
        // "data":{"errMsg":"系统繁忙，请稍后重试！","submitStatus":false},"messages":[],"validateMessages":{}}
        //JSONObject checkSubmitJson = new JSONObject(response.body());
        String body = response;
        String patternToken = "var globalRepeatSubmitToken(.*)";
        String patternForm = "var ticketInfoForPassengerForm(.*)";
        String patternOrder = "var orderRequestDTO(.*)";
        String globalRepeatSubmitToken = null;
        String ticketInfoForPassengerForm = null;
        String orderRequestDTO = null;

        Pattern rToken = Pattern.compile(patternToken);
        Matcher mToken = rToken.matcher(body);
        if (mToken.find( )) {
            globalRepeatSubmitToken = mToken.group();
        }
        Pattern compileForm = Pattern.compile(patternForm);
        Matcher matcherForm = compileForm.matcher(body);
        if(matcherForm.find()){
            ticketInfoForPassengerForm = matcherForm.group();
        }
        Pattern compileOrder = Pattern.compile(patternOrder);
        Matcher matcherOrder = compileOrder.matcher(body);
        if(matcherOrder.find()){
            orderRequestDTO = matcherOrder.group();
        }
        if(globalRepeatSubmitToken!=null&&ticketInfoForPassengerForm!=null
                &&orderRequestDTO!=null){
            globalRepeatSubmitToken = globalRepeatSubmitToken.split("=")[1]
                    .replaceAll("'","").replaceAll(";","").trim();
            ticketInfoForPassengerForm = ticketInfoForPassengerForm.split("=")[1]
                    .replaceAll(";","").trim();
            orderRequestDTO = orderRequestDTO.split("=")[1]
                    .replaceAll(";","").trim();
            JSONObject submitJson = new JSONObject();
            submitJson.put("globalRepeatSubmitToken",globalRepeatSubmitToken);
            submitJson.put("ticketInfoForPassengerForm",new JSONObject(ticketInfoForPassengerForm));
            submitJson.put("orderRequestDTO",new JSONObject(orderRequestDTO));
            log.info("第10.1步globalRepeatSubmitToken：{}",globalRepeatSubmitToken);
            log.info("第10.ticketInfoForPassengerForm：{}",ticketInfoForPassengerForm);
            log.info("第10.1步orderRequestDTO：{}",orderRequestDTO);
            return submitJson;
        }
        return null;
    }

    /**
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

    /**
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
        /*byte[] bytes = body.getBytes("UTF-8");
        String decodeStr = new String(bytes);
        body = decodeStr.replaceAll("%2B","+").replaceAll("GMT+0800","GMT%2B0800")
                .replaceAll("%28","(").replaceAll("%29",")");*/
        //end
        while(num<5||status) {
            num++;
            Thread.sleep(3000L);
            String response = httpUtil(getQueueCount, body, "POST", null);
            log.info("第12步模拟查询当前的列车排队人数的方法：{}",response);
            try{
                if(StringUtils.isNotBlank(response)){
                    JSONObject queueJson = new JSONObject(response);
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

    /**
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

    /**
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
                    ,new Date().getTime());
            String response = httpUtil(checkUserUrl,body,GET,type);
            log.info("第14步确认订单状态：{}",response);
            Thread.sleep(3000L);
            JSONObject queryOrderResult = new JSONObject(response);
            if(queryOrderResult.getBoolean("status")&&queryOrderResult.getJSONObject("data")!=null
                    &&StringUtils.isNotBlank(queryOrderResult.getJSONObject("data").getString("orderId"))){
                orderId = queryOrderResult.getJSONObject("data").getString("orderId");
            }
        }
        return orderId;
    }

    /**
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
        JSONObject queryOrderResult = new JSONObject(response);
        if(queryOrderResult.getBoolean("status")&&queryOrderResult.getJSONObject("data")!=null
                &&queryOrderResult.getJSONObject("data").getBoolean("submitStatus")){
            return true;
        }
        return false;
    }

    /**
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
            ,String body,String method, String type){
        HttpRequest httpRequest;
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(httpUrl))
                .header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        String resultBody = null;
        try {
            if("POST".equals(method)){
                httpRequest = builder.
                        POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();
            }else{
                httpRequest = builder.GET()
                        .build();
            }

            if("PNG".equals(type)){
                Path path = Paths.get(imagePath);
                HttpResponse<Path> responsePath = build.send(httpRequest,
                        HttpResponse.BodyHandlers.ofFile(path));
                resultBody = responsePath.statusCode()+"";
                log.info("httpUrl:{}返回码statusCode:{}",httpUrl,resultBody);
            }else {
                HttpResponse<String> responseStr = build.send(httpRequest,
                        HttpResponse.BodyHandlers.ofString());
                resultBody = responseStr.body();
                log.info("httpUrl:{}返回码statusCode:{}",httpUrl,responseStr.statusCode());
            }
            CookieStore cookieJar = cm.getCookieStore();
            List<HttpCookie> cookies = cookieJar.getCookies();
            for (HttpCookie cookie : cookies) {
                System.out.println(cookie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultBody;
    }

    /**
     * 转换code
     * @param result
     * @return
     */
    private static String resultToCode(String result){
        char[] chars = result.toCharArray();
        StringBuffer post = new StringBuffer();
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
        log.info("code转换：{}",codeStr);
        return codeStr;
    }
}
