package org.imzdong.study.design.auth;

/**
 * @description:
 * @author: Winter
 * @time: 2020/4/10
 */
public class ReqAuth {

    private String url;
    private String token;
    private String params;
    private Long timeStamp;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
