package com.fiie.app.network;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    // LOGIN
    @POST("api/auth/login")
    Call<JsonObject> login(@Body Map<String, String> loginData);

    // REGISTER
    @POST("api/auth/register")
    Call<String> register(@Body Map<String, String> registerData);

    // CREATE PROJECT
    @POST("api/projects")
    Call<JsonObject> createProject(
            @Query("userId") long userId,
            @Query("projectName") String projectName,
            @Query("roomType") String roomType
    );
}