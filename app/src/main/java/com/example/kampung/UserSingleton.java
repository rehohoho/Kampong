package com.example.kampung;

import com.example.kampung.models.User;

public class UserSingleton {

    private static UserSingleton instance = null;

    private UserSingleton() {
    }

    public static UserSingleton getInstance() {
        if (instance == null) {
            instance = new UserSingleton();
        }
        return instance;
    }


}
