package org.imzdong.tool.ticket.dto;

import org.imzdong.tool.ticket.base.BaseResult;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/7
 */
public class CaptchaResult extends BaseResult {
    private String value;
    public CaptchaResult(){
        super();
    }

    public CaptchaResult(boolean success, String msg){
        super(success,msg);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
