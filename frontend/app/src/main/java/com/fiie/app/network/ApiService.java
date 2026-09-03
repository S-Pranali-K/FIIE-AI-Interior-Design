package com.fiie.app.network;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/auth/login")
    Call<String> login(@Body Map<String, String> loginData);

    @POST("api/auth/register")
    Call<String> register(@Body Map<String, String> registerData);
}