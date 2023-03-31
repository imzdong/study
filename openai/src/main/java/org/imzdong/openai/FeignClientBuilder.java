package org.imzdong.openai;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import okhttp3.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author DongZhou
 * @since 2023/3/31 10:31
 */
public class FeignClientBuilder {

    private static final String proxyHost = "127.0.0.1";
    private static final int proxyPort = 7890;

    public static <T> T build(String baseUrl, Class<T> clazz, String token) {
        Feign.Builder builder = initFeignBuilder(token);
        return builder.target(clazz, baseUrl);
    }

    private static Feign.Builder initFeignBuilder(String token) {
        return Feign.builder()
                .client(new OkHttpClient(initOkhttp3Client(token)))
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder());
    }

    private static okhttp3.OkHttpClient initOkhttp3Client(String token) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new java.net.InetSocketAddress(proxyHost, proxyPort));
        return new okhttp3.OkHttpClient.Builder()
                .addInterceptor(new TokenHeaderInterceptor(token))
                .retryOnConnectionFailure(false)//是否开启缓存
                .connectionPool(pool())//连接池
                .connectTimeout(10L, TimeUnit.SECONDS)
                .readTimeout(10L, TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory(), x509TrustManager())
                .proxy(proxy)
                //.proxyAuthenticator(proxyAuthenticator)
                //.authenticator(proxyAuthenticator)
                .build();
    }

    private static class TokenHeaderInterceptor implements Interceptor {
        private String authToken;
        public TokenHeaderInterceptor(String authToken){
            this.authToken = authToken;
        }
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request updateRequest = originalRequest.newBuilder().header("Authorization", authToken).build();
            return chain.proceed(updateRequest);
        }

    }
    private static ConnectionPool pool() {
        return new ConnectionPool(100, Duration.ofMinutes(1).toMillis(), TimeUnit.MILLISECONDS);
    }
    private static SSLSocketFactory sslSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] {x509TrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("sslSocketFactory error");
    }
    private static X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {}

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {}

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    /*private static Authenticator autheticator() {
        return  (route, response) -> {
            String credential = Credentials.basic(username, password);
            return response.request().newBuilder()
                    .header("Proxy-Authorization", credential)
                    .build();
        };
    }*/

}
