package com.razy.itsmee.demo.Retrofit;

import com.razy.itsmee.demo.Models.Model_Success;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ApiInterface {

    @POST("User/")
    Call<Model_Success> register(@Body HashMap<String, String> requestBody, @Header("Content-Type") String content_type);

    @POST("Account/Login/")
    Call<Model_Success> login(@Body HashMap<String, String> requestBody, @Header("Content-Type") String content_type);


}

