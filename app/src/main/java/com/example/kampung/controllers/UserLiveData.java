package com.example.kampung.controllers;

import androidx.lifecycle.MutableLiveData;

import com.example.kampung.models.User;

/**
 * Singleton implementation of UserLiveData, referenced from UserViewModel
 */
class UserLiveData extends MutableLiveData<User> {

    private static UserLiveData userLiveData = null;

    public static UserLiveData getInstance() {
        if (userLiveData == null) {
            userLiveData = new UserLiveData();
        }
        return userLiveData;
    }

}
