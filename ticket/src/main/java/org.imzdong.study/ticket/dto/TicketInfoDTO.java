package org.imzdong.study.ticket.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TicketInfoDTO implements Serializable {

    private String seatName;

    private String ticket_num;

    private String isLeftTicket;

    private String reserve;

    private String train_no;

    private String query_from_station_name;

    private String query_to_station_name;

    private String train_location;

    private String stationTrainCode;

    private String leftTicket;

    private String start_time;

    private String arrival_time;

    private String distance_time;

    private String secretStr;

}
