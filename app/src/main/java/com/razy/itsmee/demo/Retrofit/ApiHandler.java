package com.razy.itsmee.demo.Retrofit;

import android.util.Log;

import com.razy.itsmee.demo.Login;
import com.razy.itsmee.demo.Utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHandler {

    private static final long HTTP_TIMEOUT = 30000;
    private static ApiInterface apiService;

    public static ApiInterface getApiService() {

        Log.e("JLPA",apiService+"**");
        if (apiService == null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(loggingInterceptor);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Const.base_url)
                    .client(clientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            /*
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            if (BuildConfig.DEBUG) {
                // development build
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                // production build
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            }

            httpClient.addInterceptor(logging);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Const.base_url)
                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(httpClient.build())
                    .client(getClient())
                    .build();*/
            apiService = retrofit.create(ApiInterface.class);

        }
        return apiService;
    }
    public static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build();
        return client;
    }
}
