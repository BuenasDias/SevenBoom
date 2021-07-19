package com.seven.boom.collection.api.network.apiClient;


import android.content.Context;

import com.danielceinos.cooper.CooperInterceptor;
import com.seven.boom.collection.api.network.apiService.ApiServiceMagicChecker;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientMagicChecker  {

    private static ApiClientMagicChecker mInstance;
    private static final String BASE_URL = "http://sevenvictory.fun/content/";
    private final Retrofit mRetrofit;
    private final Context mContext;

    private ApiClientMagicChecker(Context context){

        mContext = context.getApplicationContext();

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.getLevel(HttpLoggingInterceptor.Level.BODY);

//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//        httpClient.addInterceptor(chain -> {
//            Request original = chain.request();
//
//            Request request = original.newBuilder()
//                    .header("User-Agent", Params.USER_AGENT)
//                    .method(original.method(), original.body())
//                    .build();
//
//            return chain.proceed(request);
//        });

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new CooperInterceptor(mContext));

        OkHttpClient client = httpClient.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static ApiClientMagicChecker getInstance(Context context){
        if(mInstance == null){
            mInstance = new ApiClientMagicChecker(context);
        }
        return mInstance;
    }

    public ApiServiceMagicChecker getApiServiceMagicChecker(){
        return mRetrofit.create(ApiServiceMagicChecker.class);
    }
}
