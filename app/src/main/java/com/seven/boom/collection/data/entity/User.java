package com.seven.boom.collection.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public int auth;

    public String phone;

    public String name;
}
