package org.imzdong.ticket.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.imzdong.ticket.base.BaseResult;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/7
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class LoginResult extends BaseResult {

    public LoginResult(){
        super();
    }

    public LoginResult(boolean success,String msg){
        super(success,msg);
    }
}
