package org.imzdong.study.ticket.core;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/4
 */
public enum UrlConf {
    //User-Agent
    //1、登录：https://kyfw.12306.cn/otn/HttpZF/GetJS
    //Referer: https://kyfw.12306.cn/otn/resources/login.html
    GET_JS("/otn/HttpZF/GetJS","GET",false,"获取algID参数"),
    //2、判断是否需要登录验证码：https://kyfw.12306.cn/otn/login/conf
    /**
     *
     */
    LOGIN_CONF("/otn/login/conf","POST",false,"获取exp和dfp参数"),
    //2、获取设备
    LOG_DEVICE("/otn/HttpZF/logdevice","GET",false,"获取exp和dfp参数"),

    ;


    private String urlPath;
    private String type;
    private boolean isJson;
    private String desc;
    UrlConf(String urlPath,String type,boolean isJson,String desc){
        this.urlPath = urlPath;
        this.type = type;
        this.isJson = isJson;
        this.desc = desc;
    }
}
