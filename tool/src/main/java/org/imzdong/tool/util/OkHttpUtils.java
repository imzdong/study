package org.imzdong.tool.util;

import okhttp3.*;
import java.io.IOException;

public class OkHttpUtils {

    private final static OkHttpClient client = new OkHttpClient();

    public static String get(String url, Headers headers, RequestBody body) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String resp = response.body().string();
            return resp;
        }
    }
}
