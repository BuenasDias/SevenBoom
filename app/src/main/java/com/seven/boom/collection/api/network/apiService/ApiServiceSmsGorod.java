package com.seven.boom.collection.api.network.apiService;

import com.seven.boom.collection.api.requests.auth.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServiceSmsGorod {

    @GET("create?apiKey=vpuxXUEwAiBJdR0GA4CqNPcCZxmx6VlLKjvs9ntzHDosVylwzG3XH9OWxxT7&sms[0][channel]=char&sms[0][sender]=VIRTA")
    Call<Response> sendSms(@Query("sms[0][phone]") String userPhone, @Query("sms[0][text]") String fullTextSms);

}
