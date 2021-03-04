package org.imzdong.tool.geektime;

/**
 * 极客时间处理工具类
 * @author Winter
 * @date 2021-03-04
 */
public class GeekTimeUtil {

    public static String getHtml(String body){
        StringBuilder sb = new StringBuilder();
        return sb.append(GeekTimeConstant.htmlStart)
                .append(GeekTimeConstant.htmlHeaderStart)
                .append(GeekTimeConstant.htmlHeaderEnd)
                .append(GeekTimeConstant.htmlBodyStart)
                .append(body)
                .append(GeekTimeConstant.htmlBodyEnd)
                .append(GeekTimeConstant.htmlEnd).toString();
    }
}
