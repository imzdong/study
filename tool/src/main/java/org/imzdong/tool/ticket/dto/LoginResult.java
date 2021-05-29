package org.imzdong.tool.ticket.dto;

import org.imzdong.tool.ticket.base.BaseResult;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/7
 */
public class LoginResult extends BaseResult {

    public LoginResult(){
        super();
    }

    public LoginResult(boolean success,String msg){
        super(success,msg);
    }
}
