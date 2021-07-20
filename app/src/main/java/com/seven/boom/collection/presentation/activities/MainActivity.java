package com.seven.boom.collection.presentation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.seven.boom.collection.R;
import com.seven.boom.collection.api.network.apiClient.ApiClientMagicChecker;
import com.seven.boom.collection.api.requests.checker.Response;
import com.seven.boom.collection.utils.InternetConnection;
import com.seven.boom.collection.utils.Params;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private int klo;
    private Response responseBody;
    private com.seven.boom.collection.api.requests.smsGorodKey.Response responseBodyApiKey;
    private DilatingDotsProgressBar mDilatingDotsProgressBar;

//    private UserDao userDao; // Test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getUserAgent();

//        // TEST
//        AppDatabase db = App.getInstance().getDatabase();
//        userDao = db.mUserDao();
//
//        User user = new User();
//        user.id = 1;
//        user.auth = 0;
//
//        userDao.updateUser(user);
//        // TEST

        ApiClientMagicChecker.getInstance(getApplicationContext())
                .getApiServiceMagicChecker()
                .getApiKeySms()
                .enqueue(new Callback<com.seven.boom.collection.api.requests.smsGorodKey.Response>() {
                    @Override
                    public void onResponse(@NotNull Call<com.seven.boom.collection.api.requests.smsGorodKey.Response> call,
                                           retrofit2.@NotNull Response<com.seven.boom.collection.api.requests.smsGorodKey.Response> response) {

                        responseBodyApiKey = response.body();

                        Params.keyApi = responseBodyApiKey != null ? responseBodyApiKey.getKey() : "";
                    }

                    @Override
                    public void onFailure(@NotNull Call<com.seven.boom.collection.api.requests.smsGorodKey.Response> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });

        showProgressBar();

        InternetConnection.lookError(this);

        ApiClientMagicChecker.getInstance(getApplicationContext())
                .getApiServiceMagicChecker()
                .getCheckerContent()
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(@NotNull Call<Response> call, retrofit2.@NotNull Response<Response> response) {

                        hideProgressBar();

                        responseBody = response.body();
                        klo = Objects.requireNonNull(responseBody).getContent();

                        Log.d("TAG", "Retrofit. klo = " + klo);

                        Intent intent = new Intent(MainActivity.this, SlotsActivity.class);
                        intent.putExtra("cloaka", klo);

                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(@NotNull Call<Response> call, @NotNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void getUserAgent() {
        Params.USER_AGENT = new WebView(this).getSettings().getUserAgentString();
        Log.d("TAG", "getUserAgent: " + Params.USER_AGENT);
    }

    private void hideProgressBar() {
        mDilatingDotsProgressBar.hideNow();
    }

    private void showProgressBar() {
        mDilatingDotsProgressBar.showNow();
    }

    private void initView() {
        mDilatingDotsProgressBar = findViewById(R.id.progress);
    }
}