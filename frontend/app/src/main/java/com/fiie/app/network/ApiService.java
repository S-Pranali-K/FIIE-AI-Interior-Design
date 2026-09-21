package com.fiie.app.network;

import com.fiie.app.model.ProjectSurveyRequest;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    @POST("api/auth/login")
    Call<JsonObject> login(
            @Body Map<String, String> loginData
    );

    @POST("api/auth/register")
    Call<String> register(
            @Body Map<String, String> registerData
    );

    @POST("api/projects")
    Call<JsonObject> createProject(
            @Query("userId") long userId,
            @Query("projectName") String projectName,
            @Query("roomType") String roomType
    );

    @POST("api/projects/{projectId}/survey")
    Call<JsonObject> saveSurvey(
            @Path("projectId") long projectId,
            @Body ProjectSurveyRequest surveyRequest
    );

    @Multipart
    @POST("api/projects/{projectId}/images")
    Call<JsonObject> uploadRoomImage(
            @Path("projectId") long projectId,
            @Part MultipartBody.Part file
    );
}