package org.imzdong.tool.ticket.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIBuilder;
import org.imzdong.tool.ticket.dto.TicketInfoDTO;
import org.imzdong.tool.ticket.util.HttpUtil;
import org.imzdong.tool.ticket.util.UrlConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
    public static Map<String,String> seatMap = new HashMap<>();
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
    }
    private HttpClient httpClient;

    public Ticket(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 查询余票
     * @param trainDate
     * @param fromStation
     * @param toStation
     * @param purposeCode
     * @return
     * @throws Exception
     */
    public List<TicketInfoDTO> queryTicket(String trainDate,
                                                  String fromStation,
                                                  String toStation,
                                                  String purposeCode) {
        ///otn/leftTicket/queryZ?leftTicketDTO.train_date={0}&leftTicketDTO.from_station={1}&leftTicketDTO.to_station={2}&purpose_codes=ADULT
        //String ticketDate = "2019-01-28";
        //String from_station = "BJP";
        //String to_station = "NFF";
        String result = "";
        boolean queryUrlFlag = true;
        HttpGet httpGet = new HttpGet(UrlConf.QUERY_TICKET.getRequestPath());
        while (queryUrlFlag){
            String realQuery = UrlConf.QUERY_TICKET.getRequestPath();
            if(result.contains("c_url")){
                realQuery = JSONObject.parseObject(result).getString("c_url");
                queryUrlFlag = false;
            }
            try{
                URI queryUri = new URIBuilder(HttpUtil.REQUEST_HOST + realQuery + "?")
                        .setParameter("leftTicketDTO.train_date", trainDate)
                        .setParameter("leftTicketDTO.from_station", fromStation)
                        .setParameter("leftTicketDTO.to_station", toStation)
                        .setParameter("purpose_codes", purposeCode)
                        .build();
                httpGet.setUri(queryUri);
            }catch (URISyntaxException e){
                logger.error("查询余票异常：",e);
            }

            String response = HttpUtil.httpRequest(httpClient,httpGet);//HttpClientUtil.httpRequest(queryPath,httpGet);
            logger.info("第八步查票：{}",response);
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
                String reserve = null;//预定
                try {
                    reserve = URLDecoder.decode(ticketArray[1], "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    logger.error("设置余票信息异常：",e);
                }
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

    public static List<TicketInfoDTO> transTicket(List<TicketInfoDTO> listTicket){
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
    public boolean submitOrder(String secretStr,
                               String trainDate,
                               String queryFromStationName,
                               String queryToStationName) {
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
        HttpPost httpPost = new HttpPost("");
        JSONObject params = new JSONObject();
        params.put("secretStr",secretStr);
        params.put("train_date",trainDate);
        params.put("back_train_date",back_train_date);
        params.put("tour_flag",tour_flag);
        params.put("purpose_codes",purpose_codes);
        params.put("query_from_station_name",queryFromStationName);
        params.put("query_to_station_name",queryToStationName);
        StringEntity entity = new StringEntity(params.toJSONString(), StandardCharsets.UTF_8);
        httpPost.setEntity(entity);
        String response = HttpUtil.httpRequest(httpClient,httpPost);//HttpClientUtil.httpRequest(confirmStationPath,httpPost);
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
    public static JSONObject thirdConfirmSubmitToken() throws Exception{
        String confirmSubmitTokenPath = "/otn/confirmPassenger/initDc";
        String response = "";//HttpClientUtil.httpRequest(confirmSubmitTokenPath,httpGet);
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

    /*
     * 确认订单
     * @param submitToken
     * @param passengerTicketStr
     * @param oldPassengerStr
     * @param randCode
     * @return
     * @throws Exception
     */
    public static String fourthConfirmSingleForQueue(JSONObject submitToken
            ,String passengerTicketStr,String oldPassengerStr,String randCode) throws Exception{
        String confirmQueue = "/otn/confirmPassenger/confirmSingleForQueue";
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
        HttpPost httpPost = new HttpPost("");
        StringEntity entity = new StringEntity(body,StandardCharsets.UTF_8);
        httpPost.setEntity(entity);
        String response = "";//HttpClientUtil.httpRequest(confirmQueue,httpPost);
        logger.info("第13步确认队列提交订单：{}",response);
        return response;
    }

}
