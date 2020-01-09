package org.imzdong.study.ticket.base;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/9
 */
public class BaseResult {

    private boolean success;
    private String msg;

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
