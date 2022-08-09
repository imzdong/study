package org.imzdong.tool.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author dong.zhou
 * @since 2022/8/9 11:24
 */
@Slf4j
public class CookieJarManager implements CookieJar {

    private final Map<String, List<Cookie>> cookieStore = new HashMap<>();

    @Override
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
        //account.geekbang.org  time.geekbang.org
        if (null != httpUrl) {
            String host = httpUrl.host();
            List<Cookie> cookies = cookieStore.get(host);
            log.info("loadForRequest: {}, cookies:{}", host, cookies);
            return cookies != null ? cookies : new ArrayList<>();
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> cookies) {
        if (httpUrl == null || CollectionUtils.isEmpty(cookies)) {
            return;
        }
        String host = httpUrl.host();
        if(Objects.equals(host, "account.geekbang.org")){
            host = "time.geekbang.org";
        }
        log.info("saveFromResponse: {}", host);
        cookieStore.put(host, cookies);
    }
}
