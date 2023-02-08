package com.example.sayali_branch_project.services;

import com.example.sayali_branch_project.models.LoginRequest;
import com.example.sayali_branch_project.models.LoginResponse;
import com.example.sayali_branch_project.models.Message;
import com.example.sayali_branch_project.models.SendMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("login/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("messages/")
    Call<List<Message>>getMessages(@Header("X-Branch-Auth-Token") String authToken);

    @Headers("Content-Type:application/json")
    @POST("messages/")
    Call<Message> sendMessage(@Body SendMessage sendMessage, @Header("X-Branch-Auth-Token") String authToken);

}
