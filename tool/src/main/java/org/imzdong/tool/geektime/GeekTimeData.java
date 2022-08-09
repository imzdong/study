package org.imzdong.tool.geektime;

import com.alibaba.fastjson.JSONObject;
import org.imzdong.tool.util.OkHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * 极客时间课程总数
 * @author winter
 * @date 2021-03-04
 */
public class GeekTimeData {

    private final static Logger logger = LoggerFactory.getLogger(GeekTimeData.class);

    public Integer getCourseCount(){
        String url = GeekTimeConstant.dataUrl;
        Map<String, String> headerMap = GeekTimeConstant.headers;
        try {
            String resp = OkHttpUtils.http(url, OkHttpUtils.getHeaders(headerMap), OkHttpUtils.getEmptyRequestBody());
            JSONObject result = JSONObject.parseObject(resp);
            if(result.containsKey("code")&&"0".equals(result.getString("code"))){
                JSONObject data = result.getJSONObject("data");
                return data.getInteger("columns_count");
            }else {
                logger.error("获取课程总数返回code：{}，返回错误信息：{}",
                        result.getString("code"),
                        result.getString("error"));
            }
        } catch (IOException e) {
            logger.error("获取课程总数异常：", e);
        }
        return 0;
    }

}
