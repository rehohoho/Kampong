package com.example.kampung.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kampung.models.User;


/**
 * ViewModel helps prepare data for UI, retained during configuration changes,
 * available to next fragment / activity instance.
 *
 * Singleton implementation
 */
public class UserViewModel extends ViewModel {

    private MutableLiveData<User> userData;

    private UserViewModel() {

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
