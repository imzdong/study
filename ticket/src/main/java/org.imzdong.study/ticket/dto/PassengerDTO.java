package org.imzdong.study.ticket.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PassengerDTO implements Serializable {

    private String passenger_type;

    private String passenger_name;

    private String passenger_id_type_code;

    private String passenger_id_no;

    private String mobile_no;

}
