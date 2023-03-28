package org.imzdong.ticket.base;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/9
 */
public class BaseResult {

    private boolean success;
    private String msg;

    public BaseResult(){

    }

    public BaseResult(boolean success,String msg){
        this.success = success;
        this.msg = msg;
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
