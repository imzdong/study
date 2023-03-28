package org.imzdong.ticket.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PassengerDTO implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1851001942007893619L;

    private String passenger_type;

    private String passenger_name;

    private String passenger_id_type_code;

    private String passenger_id_no;

    private String mobile_no;

}
