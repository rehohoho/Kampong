package com.example.kampung.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties // ignore class fields not mapped to properties
public class User {

    public String username;
    public String telegramHandle;
    public int number;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String telegramHandle) {
        this.username = username;
        this.telegramHandle = telegramHandle;
    }

    public User(String username, String telegramHandle, int number) {
        this.username = username;
        this.telegramHandle = telegramHandle;
        this.number = number;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", telegramHandle='" + telegramHandle + '\'' +
                ", number=" + number +
                '}';
    }
}
