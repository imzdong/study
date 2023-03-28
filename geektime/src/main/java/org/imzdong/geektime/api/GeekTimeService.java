package org.imzdong.geektime.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.imzdong.model.geektime.GeekTimeResponse;
import org.imzdong.model.geektime.LoginRequest;
import org.imzdong.model.geektime.LoginResponse;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author DongZhou
 * @since 2023/3/27 17:26
 */
@Slf4j
public class GeekTimeService {

    private static final String BASE_URL = "https://account.geekbang.org";
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new AuthenticationInterceptor())
            .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
            .readTimeout(DEFAULT_TIMEOUT.toMillis(), TimeUnit.MILLISECONDS)
            .build();


    public static void main(String[] args) {
        GeekTimeApi geekTimeApi = buildApi();
        LoginRequest loginRequest = LoginRequest.builder().appid("1")
                .country("86")
                .captcha("").remember("1")
                .cellphone("18500299025")
                .password("zH&646555448")
                .learnStatus("0").platform("3").build();
        Single<GeekTimeResponse<LoginResponse>> login = geekTimeApi.login(loginRequest);
        GeekTimeResponse<LoginResponse> loginResponseGeekTimeResponse = login.blockingGet();
        LoginResponse data = loginResponseGeekTimeResponse.getData();
//        log.info("data:{}", data);
    }

    private static GeekTimeApi buildApi() {
        ObjectMapper mapper = defaultObjectMapper();
        Retrofit retrofit = defaultRetrofit(client, mapper);
        return retrofit.create(GeekTimeApi.class);
    }

    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return mapper;
    }


    private static Retrofit defaultRetrofit(OkHttpClient client, ObjectMapper mapper) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
