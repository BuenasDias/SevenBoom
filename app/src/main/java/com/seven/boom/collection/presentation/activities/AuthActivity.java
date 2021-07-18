package com.seven.boom.collection.presentation.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.seven.boom.collection.App;
import com.seven.boom.collection.R;
import com.seven.boom.collection.api.network.apiClient.ApiClientSmsGorod;
import com.seven.boom.collection.api.requests.auth.Response;
import com.seven.boom.collection.data.dao.UserDao;
import com.seven.boom.collection.data.database.AppDatabase;
import com.seven.boom.collection.data.entity.User;
import com.seven.boom.collection.databinding.ActivityAuthBinding;
import com.seven.boom.collection.databinding.ActivityMainBinding;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class AuthActivity extends AppCompatActivity {

    private ActivityAuthBinding mBinding;
    private UserDao userDao;
    private Response responseBody;
    private int passCode;
    private String fullTextSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        AppDatabase db = App.getInstance().getDatabase();
        userDao = db.mUserDao();
        setTitle("Добро пожаловать!");
        checkAuth();
        passCode = generateCode();
        fullTextSms = "Ваш код подтверждения: " + passCode;

        mBinding.btnAuthorization.setOnClickListener(view -> {

            if(mBinding.userPhone.getRawText().length() == 10){

                String phone = "7" + mBinding.userPhone.getRawText();
                Log.d("TAG", "7" + mBinding.userPhone.getRawText());

                ApiClientSmsGorod.getInstance()
                        .getApiServiceSmsGorod()
                        .sendSms(phone, fullTextSms)
                        .enqueue(new Callback<Response>() {
                            @Override
                            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                                responseBody = response.body();
                                if(Objects.requireNonNull(responseBody).getStatus().equalsIgnoreCase("success")){
                                    showEditPass();
                                    hideEditPhone();
                                } else {
                                    Toast.makeText(AuthActivity.this, "Ошибка ответа от сервера", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(AuthActivity.this, "Ошибка ответа от сервера", Toast.LENGTH_LONG).show();
                            }
                        });
            } else {
                mBinding.textErrorSms.setVisibility(View.VISIBLE);
                mBinding.textErrorSms.setText("Неверный формат телефона");
            }
        });

        mBinding.btnCheckSms.setOnClickListener(v -> {
            if(Integer.parseInt(mBinding.editSms.getText().toString().trim()) == passCode){

                startActivity(new Intent(this, WebViewActivity.class));

                User user = new User();
                user.id = 1;
                user.auth = 1;

                userDao.updateUser(user);

            } else {
                mBinding.textErrorSms.setVisibility(View.VISIBLE);
            }
        });

        mBinding.btnBack.setOnClickListener(v -> {
            showEditPhone();
            hideEditPass();
        });
    }

    private void hideEditPass() {
        mBinding.btnCheckSms.setVisibility(View.GONE);
        mBinding.editSms.setVisibility(View.GONE);
        mBinding.btnBack.setVisibility(View.GONE);
    }

    private void showEditPhone() {
        mBinding.userName.setVisibility(View.VISIBLE);
        mBinding.userPhone.setVisibility(View.VISIBLE);
        mBinding.btnAuthorization.setVisibility(View.VISIBLE);
    }

    private void hideEditPhone() {
        mBinding.userName.setVisibility(View.GONE);
        mBinding.userPhone.setVisibility(View.GONE);
        mBinding.btnAuthorization.setVisibility(View.GONE);
    }

    private void showEditPass() {
        mBinding.btnCheckSms.setVisibility(View.VISIBLE);
        mBinding.editSms.setVisibility(View.VISIBLE);
        mBinding.btnBack.setVisibility(View.VISIBLE);
    }

    // Generate #### number
    private int generateCode() {
        return (int) (Math.random() * 10000) + 1000;
    }

    private void checkAuth() {
        User user = userDao.getUserById(1);

        if (user.auth != 0) {
            startActivity(new Intent(this, WebViewActivity.class));
            finish();
        }
    }
}