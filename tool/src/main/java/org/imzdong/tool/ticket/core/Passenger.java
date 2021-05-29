package org.imzdong.tool.ticket.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.imzdong.tool.ticket.dto.PassengerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Winter
 * @time: 2019年12月20日, 0020 16:55
 */
public class Passenger {

    private final static Logger logger = LoggerFactory.getLogger(Passenger.class);
    private static Map<String,String> seatNo = new HashMap<>();
    static {
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
     * 获取乘客
     * @param token
     * @return
     */
    public static List<PassengerDTO> firstGetPassenger(String token) {
        String getPassengerPath = "/otn/confirmPassenger/getPassengerDTOs";
        HttpPost httpPost = new HttpPost();
        JSONObject params = new JSONObject();
        params.put("REPEAT_SUBMIT_TOKEN",token);
        StringEntity entity = new StringEntity(params.toJSONString(), "UTF-8");
        httpPost.setEntity(entity);
        String response = "";//HttpClientUtil.httpRequest(getPassengerPath,httpPost);
        logger.info("第九步获取乘客：{}",response);
        JSONObject passengerJson = JSONObject.parseObject(response);
        boolean status = passengerJson.getBoolean("status");
        List<PassengerDTO> passList = new ArrayList<>();
        if(status){
            JSONObject data = passengerJson.getJSONObject("data");
            if(data!=null&&data.getBoolean("isExist")){
                JSONArray normal_passengers = data.getJSONArray("normal_passengers");
                for(int num=0;num<normal_passengers.size();num++){
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
    /*
     *
     * {"passengerTicketStr":"3,0,1,乘客01,1,身份证xxx01,,N_3,0,1,乘客02,1,身份证xxx02,,N",
     * "oldPassengerStr":"乘客01,1,身份证xxx01,1_乘客02,1,身份证xxx02,1"}
     *
     * passengerTicketStr	O,0,1,文贤平,1,43052419950223XXXX,15618715583,N_O,0,1,梁敏,1,43052719920118XXXX,,N
     * oldPassengerStr	文贤平,1,43052719920118XXXX,1_梁敏,1,43052719920118XXXX,1
     * @return
     */
    public static JSONObject secondTranPassenger(List<PassengerDTO> passenger,
                                           String seatName, String[] ticketPass) throws Exception{
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
        logger.info("passengerTicketStr:{}",passengerTicketStr);
        logger.info("oldPassengerStr:{}",oldPassengerStr);
        passJson.put("passengerTicketStr",passengerTicketStr);
        passJson.put("oldPassengerStr",oldPassengerStr);
        return passJson;
    }
}
