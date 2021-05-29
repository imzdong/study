package org.imzdong.tool.ticket.util;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/4
 */
public enum UrlConf {
    OTN_INIT("/otn/leftTicket/init?linktypeid=dc",HttpUtil.GET,false,false,"初始登录页面获取js"),
    GET_JS("/otn/HttpZF/GetJS",HttpUtil.GET,false,false,"获取algID参数"),
    LOG_DEVICE("/otn/HttpZF/logdevice",HttpUtil.GET,true,true,"获取exp和dfp参数"),
    LOGIN_CONF("/otn/login/conf",HttpUtil.POST,true,true,"判断用户登录状态（是否需要验证码）"),
    LOGIN_BANNER("/otn/index12306/getLoginBanner",HttpUtil.GET,true,true,"初始化登录数据"),
    UAMTK_STATIC("/passport/web/auth/uamtk-static",HttpUtil.POST,false,true,"获取uamtk"),
    CAPTCHA_IMAGE64("/passport/captcha/captcha-image64",HttpUtil.GET,false,true,"获取登录验证码"),
    CAPTCHA_CHECK("/passport/captcha/captcha-check",HttpUtil.GET,false,false,"验证登录验证码"),
    WEB_LOGIN("/passport/web/login",HttpUtil.POST,false,false,"登录"),

    CHECK_USER("/otn/login/checkUser",HttpUtil.POST,true,false,"校验用户是否登录"),
    QUERY_TICKET("/otn/leftTicket/queryA",HttpUtil.POST,false,true,"查询余票"),
    SUBMIT_ORDER("/otn/leftTicket/submitOrderRequest",HttpUtil.POST,false,true,"提交订单"),
    ;

    private String requestPath;
    private String type;
    private String desc;
    private boolean isJson;
    private boolean isXml;
    UrlConf(String requestPath,String type,
            boolean isJson,boolean isXml,
            String desc){
        this.requestPath = requestPath;
        this.desc = desc;
        this.isJson = isJson;
        this.isXml = isXml;
        this.type = type;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isJson() {
        return isJson;
    }

    public void setJson(boolean json) {
        isJson = json;
    }

    public boolean isXml() {
        return isXml;
    }

    public void setXml(boolean xml) {
        isXml = xml;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
