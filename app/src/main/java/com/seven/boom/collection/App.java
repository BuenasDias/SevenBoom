package com.seven.boom.collection;

import android.app.Application;

import androidx.room.Room;

import com.onesignal.OneSignal;
import com.seven.boom.collection.data.database.AppDatabase;
import com.seven.boom.collection.data.entity.User;

public class App extends Application {

    public static App instance;

    private AppDatabase mDatabase;

    private static final String ONESIGNAL_APP_ID = "cb1002bb-2b29-4241-8cde-06854fc8b4dc";

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        instance = this;
        mDatabase = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();

        if (mDatabase.mUserDao().getUserById(1) == null) {

            User user = new User();
            user.id = 1;
            user.auth = 0;
            user.name = "test";
            user.phone = "test";

            mDatabase.mUserDao().insertUser(user);
        }
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return mDatabase;
    }
}
