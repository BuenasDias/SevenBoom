package com.seven.boom.collection.presentation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.seven.boom.collection.R;
import com.seven.boom.collection.utils.InternetConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InternetConnection.lookError(this);

        // TODO тут нужно сделать прелоадер
        // TODO тут нужно запустить апи и от ответа от сервера запускать игру или с джекпотом
        //  всегда или бесконечную игру




    }
}