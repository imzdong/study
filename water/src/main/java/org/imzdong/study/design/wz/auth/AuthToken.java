package org.imzdong.study.design.wz.auth;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Objects;

/**
 * @description: 鉴权token
 * @author: Winter
 * @time: 2020/4/10
 */
public class AuthToken {

    private String appId;
    private String token;

    public AuthToken(String appId) {
        this.appId = appId;
    }

    public AuthToken(String appId, String token) {
        this.appId = appId;
        this.token = token;
    }

    public AuthToken generate(String params, Long time){
        this.token = DigestUtils.md5Hex(appId+time+params);
        return this;
    }

    public boolean match(AuthToken authToken){
        return Objects.equals(authToken.appId,appId)&&Objects.equals(authToken.token,token);
    }

    public boolean isExpire(Long timeStamp){
        return true;
    }
}
