package com.santhos.truelocker.ApiService;

import com.santhos.truelocker.MyConst.MyConst;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static Retrofit instance = null;
    public static Retrofit getInstance(String url) {
        if(instance == null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request req = chain.request();
                    Request.Builder builder = req.newBuilder().addHeader("Authorization" , MyConst.CREDENTAILS)
                            .method(req.method() , req.body());
                    Request request = builder.build();
                    return chain.proceed(request);
                }
            }).build();
            instance = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return instance;
    }
}