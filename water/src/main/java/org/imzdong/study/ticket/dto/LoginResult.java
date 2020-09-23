package org.imzdong.study.ticket.dto;

import org.imzdong.study.ticket.base.BaseResult;

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
