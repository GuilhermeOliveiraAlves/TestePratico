package com.avalicaopraticast.avalicaopraticast.apis;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThemoviedbApi {
    static public IThemoviedbApi getApi(String endpoint) {

        OkHttpClient httpClient = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())

                .client(httpClient)
                .build();

        IThemoviedbApi service = retrofit.create(IThemoviedbApi.class);

        return service;

    }
}
