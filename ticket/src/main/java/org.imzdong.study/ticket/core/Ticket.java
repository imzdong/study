package org.imzdong.study.ticket.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.imzdong.study.ticket.dto.PassengerDTO;
import org.imzdong.study.ticket.dto.TicketInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: Winter
 * @time: 2019年12月20日, 0020 13:55
 */
public class Ticket {

    private final static Logger logger = LoggerFactory.getLogger(Ticket.class);
    private final static HttpGet httpGet = new HttpGet();
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
     * 查询余票
     * @param ticketDate
     * @param from_station
     * @param to_station
     * @return
     * @throws Exception
     */
    private static List<TicketInfoDTO> firstQueryTicket(String ticketDate,
                                                        String from_station,
                                                        String to_station) throws Exception{
        ///otn/leftTicket/queryZ?leftTicketDTO.train_date={0}&leftTicketDTO.from_station={1}&leftTicketDTO.to_station={2}&purpose_codes=ADULT
        //String ticketDate = "2019-01-28";
        //String from_station = "BJP";
        //String to_station = "NFF";
        String result = "";
        boolean queryUrlFlag = true;
        while (queryUrlFlag){
            String realQuery = "leftTicket/queryA";
            if(result.contains("c_url")){
                realQuery = JSONObject.parseObject(result).getString("c_url");
                queryUrlFlag = false;
            }
            Thread.sleep(1000L);
            String queryPath = "/otn/"+realQuery+"?" + String.format(
                            "leftTicketDTO.train_date=%s" +
                            "&leftTicketDTO.from_station=%s" +
                            "&leftTicketDTO.to_station=%s&purpose_codes=ADULT"
                    ,ticketDate,from_station,to_station);
            String response = HttpClientUtil.httpRequest(queryPath,httpGet);
            logger.info("第八步查票：{}---{}",queryPath,response);
            result = response;
        }
        JSONObject ticketJson = JSONObject.parseObject(result);
        JSONObject resultT = ticketJson.getJSONObject("data");
        JSONArray resultArray = resultT.getJSONArray("result");
        List<TicketInfoDTO> listTicket = new ArrayList<>();
        Set<String> seatIndexs = seatMap.keySet();
        for(int numT=0;numT<resultArray.size();numT++){
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
                    && StringUtils.isNotBlank(tto.getTicket_num())&&!"无".equals(tto.getTicket_num())
                    &&!"*".equals(tto.getTicket_num())){
                listTransTicket.add(tto);
            }
        }
        logger.info("有票：{}",listTransTicket.toString());
        return listTransTicket;
    }

    /**
     * 确认班次
     * @param secretStr
     * @param trainDate
     * @param queryFromStationName
     * @param queryToStationName
     * @return
     */
    private static boolean secondConfirmStation(String secretStr,String trainDate,
                                                      String queryFromStationName,
                                                      String queryToStationName) {
        String confirmStationPath = "/otn/leftTicket/submitOrderRequest";
        //String seatType = seatNo.get(seatName);
        //String secretStr = ""; //字符串加密
        //String train_date = "2019-01-28";//出发时间
        String back_train_date = ""; //返程时间
        String tour_flag = "dc";//旅途类型
        String purpose_codes = "ADULT";//成人票还是学生票
        //String query_from_station_name = "BJP";//起始车站
        //String query_to_station_name = "NFF";//终点车站
        //"secretStr=%s&train_date=%s&back_train_date=%s&tour_flag=%s" +
        //                        "&purpose_codes=%s&query_from_station_name=%s&query_to_station_name=%s"
        HttpPost httpPost = new HttpPost();
        JSONObject params = new JSONObject();
        params.put("secretStr",secretStr);
        params.put("train_date",trainDate);
        params.put("back_train_date",back_train_date);
        params.put("tour_flag",tour_flag);
        params.put("purpose_codes",purpose_codes);
        params.put("query_from_station_name",queryFromStationName);
        params.put("query_to_station_name",queryToStationName);
        StringEntity entity = new StringEntity(params.toJSONString(), "UTF-8");
        httpPost.setEntity(entity);
        String response = HttpClientUtil.httpRequest(confirmStationPath,httpPost);
        //{"validateMessagesShowId":"_validatorMessage",
        // "status":true,"httpstatus":200,"data":"N","messages":[],"validateMessages":{}}
        JSONObject submitJson = JSONObject.parseObject(response);
        if("N".equals(submitJson.getString("data"))){
            return true;
        }else{
            return false;
        }
    }

    /*
     * 获取提交token
     * @param secretStr
     * @return
     */
    private static JSONObject thirdConfirmSubmitToken() throws Exception{
        String confirmSubmitTokenPath = "/otn/confirmPassenger/initDc";
        String response = HttpClientUtil.httpRequest(confirmSubmitTokenPath,httpGet);
        logger.info("获取提交订单Token：{}",response);
        //{"validateMessagesShowId":"_validatorMessage","status":true,"httpstatus":200,
        // "data":{"errMsg":"系统繁忙，请稍后重试！","submitStatus":false},"messages":[],"validateMessages":{}}
        //JSONObject checkSubmitJson = new JSONObject(response.body());
        String patternToken = "var globalRepeatSubmitToken(.*)";
        String patternForm = "var ticketInfoForPassengerForm(.*)";
        String patternOrder = "var orderRequestDTO(.*)";
        String globalRepeatSubmitToken = null;
        String ticketInfoForPassengerForm = null;
        String orderRequestDTO = null;
        Pattern rToken = Pattern.compile(patternToken);
        Matcher mToken = rToken.matcher(response);
        if (mToken.find( )) {
            globalRepeatSubmitToken = mToken.group();
        }
        Pattern compileForm = Pattern.compile(patternForm);
        Matcher matcherForm = compileForm.matcher(response);
        if(matcherForm.find()){
            ticketInfoForPassengerForm = matcherForm.group();
        }
        Pattern compileOrder = Pattern.compile(patternOrder);
        Matcher matcherOrder = compileOrder.matcher(response);
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
            submitJson.put("ticketInfoForPassengerForm",JSONObject.parseObject(ticketInfoForPassengerForm));
            submitJson.put("orderRequestDTO",JSONObject.parseObject(orderRequestDTO));
            logger.info("globalRepeatSubmitToken：{}",globalRepeatSubmitToken);
            logger.info("ticketInfoForPassengerForm：{}",ticketInfoForPassengerForm);
            logger.info("orderRequestDTO：{}",orderRequestDTO);
            return submitJson;
        }
        return null;
    }

}
