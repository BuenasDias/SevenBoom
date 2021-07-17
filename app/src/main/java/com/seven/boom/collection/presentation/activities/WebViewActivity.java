package com.seven.boom.collection.presentation.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.seven.boom.collection.R;
import com.seven.boom.collection.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {

    private ActivityWebViewBinding mBinding;
    private String url;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        mBinding.webView.getSettings().setJavaScriptEnabled(true);
        mBinding.webView.setWebViewClient(new MyWebViewClient());

        // включаем само приложение в браузере
        if (savedInstanceState == null) {
            mBinding.webView.loadUrl(url);
        } else {
            mBinding.webView.restoreState(savedInstanceState);
        }
    }

    // тут обрабатываем попорот экрана
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mBinding.webView.saveState(outState);
    }

    @Override
    public void onBackPressed() {
        if(mBinding.webView.canGoBack()) {
            mBinding.webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}