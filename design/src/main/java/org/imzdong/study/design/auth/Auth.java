package org.imzdong.study.design.auth;

/**
 * @description:
 * @author: Winter
 * @time: 2020/4/10
 */
public class Auth {

    public boolean auth(ReqAuth reqAuth){
        AuthToken clientToken = new AuthToken(reqAuth.getUrl(), reqAuth.getToken());
        clientToken.isExpire(reqAuth.getTimeStamp());
        //getServerAppId(url);
        String serverAppId = "";
        AuthToken serverToken = new AuthToken(serverAppId);
        serverToken = serverToken.generate(reqAuth.getParams(), reqAuth.getTimeStamp());
        return clientToken.match(serverToken);
    }
}
