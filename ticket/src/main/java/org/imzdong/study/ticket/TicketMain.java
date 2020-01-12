package org.imzdong.study.ticket;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.imzdong.study.ticket.core.Order;
import org.imzdong.study.ticket.core.Passenger;
import org.imzdong.study.ticket.core.Ticket;
import org.imzdong.study.ticket.dto.PassengerDTO;
import org.imzdong.study.ticket.dto.TicketInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/*
 * @description:
 * @author: Winter
 * @time: 2019/12/18
*/
public class TicketMain {
    private final static Logger log = LoggerFactory.getLogger(TicketMain.class);
    /*
     * 1、https://kyfw.12306.cn/otn/leftTicket/init   （left_ticket_init）
     * 2、https://kyfw.12306.cn/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&{0}  （getCodeImg）
     * 2.1、https://kyfw.12306.cn/passport/web/auth/uamtk   "appid": "otn"  （auth） 登录认证接口
     * 3、https://kyfw.12306.cn/passport/captcha/captcha-check （codeCheck）
     * 3、https://kyfw.12306.cn/passport/web/login
     * {
     *  "username": user,
     *  "password": passwd,
     *  "appid": "otn"
     * }   登录成功
     */
    public static void main(String[] args)throws Exception{
        String userName = "xx";
        String password = "xx";
        String seatName = "硬卧";
        String[] ticketPass = {"xx","xx"};
        String from = "BJP";
        String to = "NFF";
        String trainDate = "2020-01-31";
        String trainCode = "K261";
        log.info("start:{}",new Date());
        log.info("1、初始化登陆：/otn/leftTicket/init");
        String callBack = "jQuery191025909781158866285_1577623706238";
        Long nums = System.currentTimeMillis();
        //Login.firstInit();
        //Login.secondGetCode(nums,callBack);
        //String algID = Login.first2GetJs();
        //String logDeviceParams = Login.first4GetLogDeviceParams();
        //Login.first3LogDevice(algID, logDeviceParams);
        log.info("2、获取验证码：/passport/captcha/captcha-image");
        //Login.first5Conf();
        //Login.secondGetCode(nums,callBack);
        //secondOAuth();
        Scanner scan = new Scanner(System.in);
        System.out.print("输入整数：");
        String code = "";
        int result12306 = 0;
        String umktk,randCode="00";
        while((scan.hasNextLine()&&4 != result12306)){
            code = scan.next();
            if("closed".equals(code)){
                break;
            }
            String s = "";//Login.thirdInputCode(code);//fourCheckCode(code);
            randCode = s;
            //{"result_message":"验证码校验成功","result_code":"4"}
            log.info("3、校验验证码：/passport/captcha/captcha-check");
            //result12306 = Login.fourthCheckCode(s,callBack,nums);
        }
        String algID = "";//Login.first2GetJs();
        //String logDeviceParams = Login.first4GetLogDeviceParams();
        //Login.first3LogDevice(algID);
        log.info("4、12306用户账号登陆：/passport/web/login");
        umktk = "";//Login.fifthWebLogin(userName,password,randCode);
        log.info("umktk：{}",umktk);
        if(StringUtils.isNotBlank(umktk)){
            log.info("5、12306获取用户apptk：/passport/web/auth/uamtk");
            //String newAppTk = Login.sixAuth();//newapptk
            //log.info("newapptk：{}",newAppTk);
            log.info("6、12306获取用户cookie：otn/uamauthclient");
            //getUser(newAppTk);
            //checkUser();//校验是否登录
            log.info("7、查询余票：leftTicket/query");
            List<TicketInfoDTO> ticketInfoDTOS = Ticket.firstQueryTicket(trainDate,from,to);
            log.info("ticketInfoDTOS：{}",ticketInfoDTOS);
            log.info("ticketInfoDTOS size：{}",ticketInfoDTOS.size());
            ticketInfoDTOS = Ticket.transTicket(ticketInfoDTOS);
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
                boolean reserveSubmitOrder = Ticket.secondConfirmStation(ticketInfoDTOS.get(0).getSecretStr(),
                        trainDate,
                        from, to);
                if(reserveSubmitOrder){
                    log.info("9、进入预定单程页面：/otn/confirmPassenger/initDc");
                    JSONObject initDcSubmitToken = Ticket.thirdConfirmSubmitToken();
                    if(initDcSubmitToken!=null) {
                        //获取订单提交token
                        String globalRepeatSubmitToken = initDcSubmitToken.getString("globalRepeatSubmitToken");
                        log.info("10、获取乘客信息：/otn/confirmPassenger/getPassengerDTOs");
                        List<PassengerDTO> passenger = Passenger.firstGetPassenger(globalRepeatSubmitToken);
                        if(passenger.size()>0) {
                            JSONObject passengerJson = Passenger.secondTranPassenger(passenger, seatName, ticketPass);
                            log.info("符合目标的乘客数据：{}", passengerJson);
                            log.info("11、确认订单信息：/otn/confirmPassenger/checkOrderInfo");
                            String checkOrder = Order.firstCheckOrder(globalRepeatSubmitToken
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
                                String queueCount = Order.secondGetQueueCount(initDcSubmitToken, Ticket.seatMap.get(seatName), trainDate);
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
                                            String confirmSingleForQueue = Ticket.fourthConfirmSingleForQueue(initDcSubmitToken, passengerJson.getString("passengerTicketStr")
                                                    , passengerJson.getString("oldPassengerStr")
                                                    , "");
                                            JSONObject confirmQueueJson = JSONObject.parseObject(confirmSingleForQueue);
                                            if(confirmQueueJson.getBoolean("status")&&confirmQueueJson.getJSONObject("data")!=null
                                                    &&confirmQueueJson.getJSONObject("data").getBoolean("submitStatus")){
                                                log.info("14、下单成功，查询订单号：/otn/confirmPassenger/queryOrderWaitTime");
                                                String orderId = Order.thirdQueryOrderWaitTime(globalRepeatSubmitToken);
                                                if(orderId!=null){
                                                    log.info("15、下单成功，确认订单状态：/otn/confirmPassenger/resultOrderForDcQueue");
                                                    boolean resultOrderForDcQueue = Order.fourthResultOrderForDcQueue(globalRepeatSubmitToken, orderId);
                                                    log.info("16、下单成功，获取订单详情：/otn/queryOrder/queryMyOrderNoComplete");
                                                    Order.fivethQueryMyOrderNoComplete();
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

}
