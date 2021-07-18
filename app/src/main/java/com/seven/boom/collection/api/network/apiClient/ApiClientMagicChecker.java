package com.seven.boom.collection.api.network.apiClient;

import com.seven.boom.collection.api.network.apiService.ApiServiceMagicChecker;
import com.seven.boom.collection.api.network.apiService.ApiServiceSmsGorod;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClientMagicChecker {

    private static ApiClientMagicChecker mInstance;
    private static final String BASE_URL = "http://sevenvictory.fun/content/";
    private final Retrofit mRetrofit;

    private ApiClientMagicChecker(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    public static ApiClientMagicChecker getInstance(){
        if(mInstance == null){
            mInstance = new ApiClientMagicChecker();
        }
        return mInstance;
    }

    public ApiServiceMagicChecker getApiServiceMagicChecker(){
        return mRetrofit.create(ApiServiceMagicChecker.class);
    }



}
