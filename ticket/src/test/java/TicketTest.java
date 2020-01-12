import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import org.imzdong.study.ticket.core.Ticket;
import org.imzdong.study.ticket.dto.TicketInfoDTO;
import org.imzdong.study.ticket.util.HttpUtil;

import java.util.List;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/12
 */
public class TicketTest {

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpUtil.getHttpClient(3000);
        Ticket ticket = new Ticket(httpClient);
        //String ticketDate = "2019-01-28";
        //String from_station = "BJP";
        //String to_station = "NFF";
        String tranDate = "2020-01-14";
        String from = "BJP";
        String to= "NFF";
        String code = "ADULT";
        List<TicketInfoDTO> ticketInfoDTOS = ticket.queryTicket(tranDate, from, to, code);
        System.out.println(JSONObject.toJSONString(ticketInfoDTOS));
    }
}
