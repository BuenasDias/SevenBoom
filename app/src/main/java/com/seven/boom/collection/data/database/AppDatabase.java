package com.seven.boom.collection.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.seven.boom.collection.data.dao.UserDao;
import com.seven.boom.collection.data.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao mUserDao();
}
