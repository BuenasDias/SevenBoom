package com.seven.boom.collection.api.network.apiService;

import com.seven.boom.collection.api.requests.auth.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServiceSmsGorod {

    // create?apiKey=vpuxXUEwAiBJdR0GA4CqNPcCZxmx6VlLKjvs9ntzHDosVylwzG3XH9OWxxT7&sms[0][channel]=char&sms[0][phone]=79536506580&sms[0][text]=Ваш код подтверждения: 53486&sms[0][sender]=VIRTA

    @GET("create?apiKey=vpuxXUEwAiBJdR0GA4CqNPcCZxmx6VlLKjvs9ntzHDosVylwzG3XH9OWxxT7&sms[0][channel]=char&sms[0][phone]={phone}&sms[0][text]=Ваш код подтверждения: {smsCode}&sms[0][sender]=VIRTA")
    Call<Response> sendSms(@Path("phone") String userPhone, @Path("smsCode") int smsCode);

}
