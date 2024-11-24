package org.imzdong.geektime;

import java.util.HashMap;
import java.util.Map;

/**
 * 极客时间常量池
 * @author winter
 * @date 2021-03-04
 */
public class GeekTimeConstant {

    public final static String origin = "https://time.geekbang.org";
    public final static String loginUrl = "https://account.geekbang.org/account/ticket/login";
    public final static String dataUrl = origin + "/serv/v1/my/data";
    public final static String courseUrl = origin + "/serv/v3/learn/product";
    /**
     *         获取课程列表  'https://time.geekbang.org/serv/v1/column/all'
     *         :return:
     *             key: value
     *             '1'
     *             '2'
     */
    public final static String articlesUrl = origin + "/serv/v1/column/articles";
    public final static String articleUrl = origin + "/serv/v1/article";
    public final static String articleCommentsUrl = origin + "/serv/v1/comments";

    public final static String introUrl = origin + "/serv/v1/column/intro";
    public final static String chaptersUrl = origin + "/serv/v1/chapters";

    public final static String cookie = "Cookie";

    public final static String templateDir = "template";
    public final static String template = "article.ftlh";

    public final static String htmlStart = "<html>";
    public final static String htmlEnd = "</html>";
    public final static String htmlHeaderStart = "<head>";
    public final static String htmlHeaderEnd = "</head>";
    public final static String htmlBodyStart = "<body>";
    public final static String htmlBodyEnd = "</body>";

    public final static Map<String, String> headers = new HashMap<>();
    public final static String accept = "application/json, text/plain, */*";
    public final static String acceptEncoding = "gzip, deflate, br";

    public final static String acceptLanguage = "zh-CN,zh;q=0.9";
    public final static String connection = "keep-alive";
    public final static String contentType = "application/json";
    public final static String host = "time.geekbang.org";
    public final static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.122 Safari/537.36";
    //Content-Length: 59
    //Cookie: gksskpitn=c1e0be11-7b23-4e06-ae43-0d31caa2d391; sajssdk_2015_cross_new_user=1; LF_ID=1614757077875-6552078-9033480; _ga=GA1.2.932290469.1614757078; _gid=GA1.2.160552720.1614757078; GCID=35beba1-db80763-f48c744-fa403f6; GRID=35beba1-db80763-f48c744-fa403f6; GCESS=BQkBAQsCBQAKBAAAAAAEBAAvDQAFBAAAAAAMAQEHBOLY9RICBPM8P2AIAQMDBPM8P2AGBMN3A_kBCJwPEgAAAAAA; Hm_lvt_59c4ff31a9ee6263811b23eb921a5083=1614757078,1614757109; Hm_lvt_022f847c4e3acd44d4a2481d9187f1e6=1614757078,1614757109; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%221183644%22%2C%22first_id%22%3A%22177f705a2ba2b1-05c618065c220b-7373667-1049088-177f705a2bb723%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24latest_landing_page%22%3A%22https%3A%2F%2Ftime.geekbang.org%2Fcolumn%2Farticle%2F6458%22%7D%2C%22%24device_id%22%3A%22177f705a2ba2b1-05c618065c220b-7373667-1049088-177f705a2bb723%22%7D; _gat=1; SERVERID=1fa1f330efedec1559b3abbcb6e30f50|1614760395|1614757077; Hm_lpvt_59c4ff31a9ee6263811b23eb921a5083=1614760396; Hm_lpvt_022f847c4e3acd44d4a2481d9187f1e6=1614760396; gk_process_ev={%22count%22:16%2C%22target%22:%22%22%2C%22utime%22:1614757107530%2C%22referrer%22:%22https://time.geekbang.org/column/126%22%2C%22referrerTarget%22:%22%22}
    //Referer: https://time.geekbang.org/column/article/6458

    public static final String CUSTOM_COOKIE = "_ga=GA1.2.862048808.1679818512; LF_ID=6b93249-de85e48-3d28884-6df1e9f; " +
            "mantis5539=9950a03d0c64485f87fba01a33178d1c@5539; " +
            "_ga_MTX5SQH9CV=GS1.2.1720104229.1.0.1720104229.0.0.0; " +
            "MEIQIA_TRACK_ID=2NXoVZD67mAsW8zVLnuwPk4FD0h; " +
            "MEIQIA_VISIT_ID=2immhkfMP4Zxn34VIrlDfO4P9AD; " +
            "gksskpitn=c6997e73-5aee-423a-99f2-8324a2ac00f7; " +
            "Hm_lvt_59c4ff31a9ee6263811b23eb921a5083=1732430516; " +
            "HMACCOUNT=593342EE700F4024; Hm_lvt_022f847c4e3acd44d4a2481d9187f1e6=1732430516; " +
            "_gid=GA1.2.995983670.1732430517; _gat=1; GCID=1194a91-98492ae-6bdb82a-5356ec1; " +
            "_ga_JW698SFNND=GS1.2.1732430525.7.1.1732430532.0.0.0; " +
            "gk_process_ev={%22count%22:2%2C%22utime%22:1732430532646%2C%22referrer%22:%22https://time.geekbang.org/%22%2C%22target%22:%22page_geektime_login%22%2C%22referrerTarget%22:%22page_geektime_login%22}; " +
            "GRID=1194a91-98492ae-6bdb82a-5356ec1; GCESS=BggBAw0BAQsCBgAJAQEEBACNJwAKBAAAAAAFBAAAAAABCGJGDwAAAAAABgTrSGwsAwTaykJnDAEBBwTDV70TAgTaykJn; tfstk=fH6IZw6u220B62NUG0Ew54mCA4p5Oaw4N0tRmgHE2pppFU_RSXRyzg3SFZ73qkjPL1_5VZYpUpWUF_9hQ6ky8yv52ZJ7ury43MjHZLU4usdP4n9v0HL-zeItWj0EhCe43MjKeuS7k-WesScklUpJ9Qd9XFxoJLKJvlT9Vnc-JaQRXltXqHL-9X396389yLpRyGheKPtgOeIQb5I3zXzTYMTsexiDXBHhxbkmnTxBOTIp5HtCCHOBlC-IEDXd7gBFKCVmgLjNGa1RWRMCPMCAyeWTkYTPi3sJcKemcFBCVOOh_2hpcpTBM9dt0xsHw166L9UoiGS6JI9N_54eap_CiEALsPb5fe7RdC3Q7U5Vb9d1kPk1rQ1dRhpA4sM2l5-rNcOmFhT4flGoZTZtjxMAKNFe9hxN3lZsRbApjhT4flGoZBKMbIr_f2Gl.; Hm_lpvt_59c4ff31a9ee6263811b23eb921a5083=1732430547; Hm_lpvt_022f847c4e3acd44d4a2481d9187f1e6=1732430547; _ga_03JGDGP9Y3=GS1.2.1732430516.10.1.1732430547.0.0.0; __tea_cache_tokens_20000743={%22web_id%22:%227440732456057097485%22%2C%22user_unique_id%22:%221001058%22%2C%22timestamp%22:1732430547653%2C%22_type_%22:%22default%22}; SERVERID=1fa1f330efedec1559b3abbcb6e30f50|1732430559|1732430526";

    static {
        headers.put("Accept", accept);
        //headers.put("Accept-Encoding", acceptEncoding);
        headers.put("Accept-Language", acceptLanguage);
        headers.put("Connection", connection);
        headers.put("Content-Type", contentType);
        headers.put("Host", host);
        headers.put("Origin", origin);
        headers.put("User-Agent", userAgent);
        headers.put("Cookie", CUSTOM_COOKIE);
    }
}
