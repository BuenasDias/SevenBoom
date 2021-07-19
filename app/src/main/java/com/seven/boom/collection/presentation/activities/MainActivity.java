package com.seven.boom.collection.presentation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.seven.boom.collection.R;
import com.seven.boom.collection.api.network.apiClient.ApiClientMagicChecker;
import com.seven.boom.collection.api.requests.checker.Response;
import com.seven.boom.collection.utils.InternetConnection;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private int klo;
    private Response responseBody;
    private DilatingDotsProgressBar mDilatingDotsProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        showProgressBar();

        InternetConnection.lookError(this);

        ApiClientMagicChecker.getInstance()
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