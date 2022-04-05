package com.example.kampung;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kampung.models.User;


/**
 * ViewModel helps prepare data for UI, retained during configuration changes,
 * available to next fragment / activity instance.
 */
public class UserSingleton extends ViewModel {

    private MutableLiveData<User> userData;

    private UserSingleton() {

    }

    private LiveData<User> getUser() {
        if (userData == null) {
            userData = new MutableLiveData<>();
            loadUser();
        }
        return userData;
    }

    private void loadUser() {
        // asynchronously load userData here
    }

}
