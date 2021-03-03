package org.imzdong.tool.ticket.dto;

import java.io.Serializable;

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

    public String getPassenger_type() {
        return passenger_type;
    }

    public void setPassenger_type(String passenger_type) {
        this.passenger_type = passenger_type;
    }

    public String getPassenger_name() {
        return passenger_name;
    }

    public void setPassenger_name(String passenger_name) {
        this.passenger_name = passenger_name;
    }

    public String getPassenger_id_type_code() {
        return passenger_id_type_code;
    }

    public void setPassenger_id_type_code(String passenger_id_type_code) {
        this.passenger_id_type_code = passenger_id_type_code;
    }

    public String getPassenger_id_no() {
        return passenger_id_no;
    }

    public void setPassenger_id_no(String passenger_id_no) {
        this.passenger_id_no = passenger_id_no;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

}
