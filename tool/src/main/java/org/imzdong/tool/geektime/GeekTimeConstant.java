package org.imzdong.tool.geektime;

import java.util.HashMap;
import java.util.Map;

public class GeekTimeConstant {

    public final static String origin = "https://time.geekbang.org";
    public final static String articlesUrl = origin + "/serv/v1/column/articles";
    public final static String articleUrl = origin + "/serv/v1/article";
    public final static String cookie = "Cookie";

    public final static Map<String, String> headers = new HashMap<>();
    public final static String accept = "application/json, text/plain, */*";
    public final static String acceptEncoding = "gzip, deflate, br";

    public final static String acceptLanguage = "zh-CN,zh;q=0.9";
    public final static String connection = "keep-alive";
    public final static String contentType = "application/json";
    public final static String host = "time.geekbang.org";
    public final static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.122 Safari/537.36";
    public final static String articleReferer = "https://time.geekbang.org/column/article/";
    //Content-Length: 59
    //Cookie: gksskpitn=c1e0be11-7b23-4e06-ae43-0d31caa2d391; sajssdk_2015_cross_new_user=1; LF_ID=1614757077875-6552078-9033480; _ga=GA1.2.932290469.1614757078; _gid=GA1.2.160552720.1614757078; GCID=35beba1-db80763-f48c744-fa403f6; GRID=35beba1-db80763-f48c744-fa403f6; GCESS=BQkBAQsCBQAKBAAAAAAEBAAvDQAFBAAAAAAMAQEHBOLY9RICBPM8P2AIAQMDBPM8P2AGBMN3A_kBCJwPEgAAAAAA; Hm_lvt_59c4ff31a9ee6263811b23eb921a5083=1614757078,1614757109; Hm_lvt_022f847c4e3acd44d4a2481d9187f1e6=1614757078,1614757109; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%221183644%22%2C%22first_id%22%3A%22177f705a2ba2b1-05c618065c220b-7373667-1049088-177f705a2bb723%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24latest_landing_page%22%3A%22https%3A%2F%2Ftime.geekbang.org%2Fcolumn%2Farticle%2F6458%22%7D%2C%22%24device_id%22%3A%22177f705a2ba2b1-05c618065c220b-7373667-1049088-177f705a2bb723%22%7D; _gat=1; SERVERID=1fa1f330efedec1559b3abbcb6e30f50|1614760395|1614757077; Hm_lpvt_59c4ff31a9ee6263811b23eb921a5083=1614760396; Hm_lpvt_022f847c4e3acd44d4a2481d9187f1e6=1614760396; gk_process_ev={%22count%22:16%2C%22target%22:%22%22%2C%22utime%22:1614757107530%2C%22referrer%22:%22https://time.geekbang.org/column/126%22%2C%22referrerTarget%22:%22%22}
    //Referer: https://time.geekbang.org/column/article/6458
    static {
        headers.put("Accept", accept);
        headers.put("Accept-Encoding", acceptEncoding);
        headers.put("Accept-Language", acceptLanguage);
        headers.put("Connection", connection);
        headers.put("Content-Type", contentType);
        headers.put("Host", host);
        headers.put("Origin", origin);
        headers.put("User-Agent", userAgent);
    }
}
