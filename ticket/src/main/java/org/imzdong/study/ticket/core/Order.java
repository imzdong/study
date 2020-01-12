package org.imzdong.study.ticket.core;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @description:
 * @author: Winter
 * @time: 2019年12月20日, 0020 13:55
 */
public class Order {

    private final static Logger logger = LoggerFactory.getLogger(Order.class);
    /*
     * 检查支付订单，需要提交REPEAT_SUBMIT_TOKEN
     *         passengerTicketStr : 座位编号,0,票类型,乘客名,证件类型,证件号,手机号码,保存常用联系人(Y或N)
     *         oldPassengersStr: 乘客名,证件类型,证件号,乘客类型
     * @param token
     * @return
     */
    public static String firstCheckOrder(String token,String passengerTicketStr
            ,String oldPassengerStr) {
        String checkOrderUrl = "/otn/confirmPassenger/checkOrderInfo";
        //座位编号,0,票类型,乘客名,证件类型,证件号,手机号码,保存常用联系人(Y或N)
        //String passengerTicketStr = "2019-01-28";
        //String oldPassengerStr = ""; //乘客名,证件类型,证件号,乘客类型
        String randCode = "";//
        String cancel_flag = "2";
        String bed_level_order_num = "000000000000000000000000000000";
        String tour_flag = "dc";
        String _json_att = "";
        HttpPost httpPost = new HttpPost();
        JSONObject params = new JSONObject();
        params.put("passengerTicketStr",passengerTicketStr);
        params.put("oldPassengerStr",oldPassengerStr);
        params.put("REPEAT_SUBMIT_TOKEN",token);
        params.put("randCode",randCode);
        params.put("cancel_flag",cancel_flag);
        params.put("bed_level_order_num",bed_level_order_num);
        params.put("tour_flag",tour_flag);
        params.put("_json_att",_json_att);
        StringEntity entity = new StringEntity(params.toJSONString(), "UTF-8");
        httpPost.setEntity(entity);
        String response = "";//HttpClientUtil.httpRequest(checkOrderUrl,httpPost);
        logger.info("第11步检查订单：{}",response);
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
    public static String secondGetQueueCount(JSONObject submitToken,
                                              String seatType, String trainDate) throws Exception{
        String getQueueCount = "/otn/confirmPassenger/getQueueCount";
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
        logger.info("第12步模拟查询当前的列车参数:{}",body);
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
        HttpPost httpPost = new HttpPost();
        StringEntity entity = new StringEntity(body, "UTF-8");
        httpPost.setEntity(entity);
        //end
        while(num<5||status) {
            num++;
            Thread.sleep(3000L);
            String response = "";//HttpClientUtil.httpRequest(getQueueCount, httpPost);
            logger.info("第12步模拟查询当前的列车排队人数的方法：{}",response);
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
    /*
     * 确认订单状态
     * @param globalToken
     * @return
     */

    public static String thirdQueryOrderWaitTime(String globalToken) throws Exception{
        String checkUserUrl = "/otn/confirmPassenger/queryOrderWaitTime";
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
            HttpPost httpPost = new HttpPost();
            StringEntity entity = new StringEntity(body, "UTF-8");
            httpPost.setEntity(entity);
            String response = "";//HttpClientUtil.httpRequest(checkUserUrl,httpPost);
            logger.info("第14步确认订单状态：{}",response);
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
    public static boolean fourthResultOrderForDcQueue(String globalToken,String orderId) throws Exception{
        String checkUserUrl = "/otn/confirmPassenger/resultOrderForDcQueue";
        String body = String.format("_json_att=%s" +
                        "&REPEAT_SUBMIT_TOKEN=%s" +
                        "&orderSequence_no=%s"
                ,""
                ,globalToken
                ,orderId);
        HttpPost httpPost = new HttpPost();
        httpPost.setEntity(new StringEntity(body,"UTF-8"));
        String response = "";//HttpClientUtil.httpRequest(checkUserUrl,httpPost);
        logger.info("第15步获取真正订单状态：{}",response);
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

    public static String fivethQueryMyOrderNoComplete() throws Exception{
        String checkUserUrl = "/otn/queryOrder/queryMyOrderNoComplete";
        String body = String.format("_json_att=%s"
                ,"");
        HttpPost httpPost = new HttpPost();
        httpPost.setEntity(new StringEntity(body, "UTF-8"));
        String response = "";//HttpClientUtil.httpRequest(checkUserUrl, httpPost);
        logger.info("第16步获取订单详情：{}",response);
        return response;
    }

}
