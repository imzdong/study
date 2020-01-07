package org.imzdong.study.ticket.core;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/7
 */
public class LoginResult {

    private boolean success;
    private String msg;

    LoginResult(){

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
