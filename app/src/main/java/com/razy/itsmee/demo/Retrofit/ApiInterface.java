package com.razy.itsmee.demo.Retrofit;

import com.razy.itsmee.demo.Models.ListResponse;
import com.razy.itsmee.demo.Models.Model_Success;
import com.razy.itsmee.demo.Models.data;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface ApiInterface {

    @POST("User/")
    Call<Model_Success> register(@Body HashMap<String, String> requestBody, @Header("Content-Type") String content_type);

    @POST("Account/Login/")
    Call<Model_Success> login(@Body HashMap<String, String> requestBody, @Header("Content-Type") String content_type);

//    @FormUrlEncoded
    @GET("Picmee/307")
    Call<ListResponse> getdata(@Query("relationshipType") int relationshipType, @Query("PageIndex") int PageIndex, @Query("PageSize") int PageSize);
//    Call<data> getdata(@FieldMap HashMap<String, String> requestBody, @Header("Content-Type") String content_type);


}

