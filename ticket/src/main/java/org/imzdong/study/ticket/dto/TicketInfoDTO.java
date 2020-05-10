package org.imzdong.study.ticket.dto;

import lombok.Data;

import java.io.Serializable;

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

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getTicket_num() {
        return ticket_num;
    }

    public void setTicket_num(String ticket_num) {
        this.ticket_num = ticket_num;
    }

    public String getIsLeftTicket() {
        return isLeftTicket;
    }

    public void setIsLeftTicket(String isLeftTicket) {
        this.isLeftTicket = isLeftTicket;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    public String getTrain_no() {
        return train_no;
    }

    public void setTrain_no(String train_no) {
        this.train_no = train_no;
    }

    public String getQuery_from_station_name() {
        return query_from_station_name;
    }

    public void setQuery_from_station_name(String query_from_station_name) {
        this.query_from_station_name = query_from_station_name;
    }

    public String getQuery_to_station_name() {
        return query_to_station_name;
    }

    public void setQuery_to_station_name(String query_to_station_name) {
        this.query_to_station_name = query_to_station_name;
    }

    public String getTrain_location() {
        return train_location;
    }

    public void setTrain_location(String train_location) {
        this.train_location = train_location;
    }

    public String getStationTrainCode() {
        return stationTrainCode;
    }

    public void setStationTrainCode(String stationTrainCode) {
        this.stationTrainCode = stationTrainCode;
    }

    public String getLeftTicket() {
        return leftTicket;
    }

    public void setLeftTicket(String leftTicket) {
        this.leftTicket = leftTicket;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getDistance_time() {
        return distance_time;
    }

    public void setDistance_time(String distance_time) {
        this.distance_time = distance_time;
    }

    public String getSecretStr() {
        return secretStr;
    }

    public void setSecretStr(String secretStr) {
        this.secretStr = secretStr;
    }

    

}
