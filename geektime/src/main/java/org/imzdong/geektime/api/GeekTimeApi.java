package org.imzdong.geektime.api;


import io.reactivex.Single;
import org.imzdong.model.geektime.GeekTimeResponse;
import org.imzdong.model.geektime.LoginRequest;
import org.imzdong.model.geektime.LoginResponse;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GeekTimeApi {

    @POST("/account/ticket/login")
    Single<GeekTimeResponse<LoginResponse>> login(@Body LoginRequest loginRequest);
}
