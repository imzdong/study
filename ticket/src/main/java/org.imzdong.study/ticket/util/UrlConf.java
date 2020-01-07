package org.imzdong.study.ticket.util;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/4
 */
public enum UrlConf {
    GET_JS("https://kyfw.12306.cn/otn/HttpZF/GetJS",HttpUtil.GET,false,"获取algID参数"),
    LOG_DEVICE("https://kyfw.12306.cn/otn/HttpZF/logdevice",HttpUtil.GET,false,"获取exp和dfp参数"),
    LOGIN_CONF("https://kyfw.12306.cn/otn/login/conf",HttpUtil.POST,false,"获取exp和dfp参数"),

    ;

    private String url;
    private String type;
    private boolean isJson;
    private String desc;
    UrlConf(String url,String type,
            boolean isJson,String desc){
        this.url = url;
        this.type = type;
        this.isJson = isJson;
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isJson() {
        return isJson;
    }

    public void setJson(boolean json) {
        isJson = json;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
