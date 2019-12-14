package com.santhos.truelocker.ApiService;

import com.santhos.truelocker.Models.Response;
import com.santhos.truelocker.Models.Status;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {



    @GET("close_lock1/")
    Observable<Response> getClose();

    @GET("l_status")
    Observable<Status> getStatus();

    @GET("open_lock1/")
    Observable<Response> getOpen();

    @GET("Req_key")
    Observable<Response> getOtp();

    @GET("otp_access/{otp}")
    Observable<Response> getVerifyOtp(@Query("otp") Integer otp);
}
