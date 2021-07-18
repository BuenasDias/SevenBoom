package com.seven.boom.collection.api.network.apiService;

import com.seven.boom.collection.api.requests.checker.Response;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServiceMagicChecker {

    @GET("index.php")
    Call<Response> getCheckerContent();
}
