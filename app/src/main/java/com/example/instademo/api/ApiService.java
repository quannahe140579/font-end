package com.example.instademo.api;

import com.example.instademo.dto.UserDTO;
import com.example.instademo.model.LoginForm;
import com.example.instademo.model.RegisterForm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.5:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @POST("/api/users/login")
    Call<UserDTO> getUser(@Body LoginForm form);

    @POST("/api/users/register")
    Call<UserDTO> register(@Body RegisterForm form);
}
