package com.razy.itsmee.demo.Retrofit;

import com.razy.itsmee.demo.Model_Success;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("User/")
    Call<Model_Success> register(@FieldMap HashMap<String, String> requestBody, @Header("Content-Type") String content_type);


    @FormUrlEncoded
    @POST("Account/Login/")
    Call<Model_Success> login(@FieldMap HashMap<String, String> requestBody, @Header("Content-Type") String content_type);


}

