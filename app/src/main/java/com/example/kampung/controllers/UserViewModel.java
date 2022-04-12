package com.example.kampung.controllers;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kampung.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


/**
 * ViewModel helps prepare data for UI, retained during configuration changes,
 * available to next fragment / activity instance.
 *
 * Singleton implementation
 */
public class UserViewModel extends ViewModel {

    private final String TAG = "UserViewModel";
    private final UserLiveData userData = UserLiveData.getInstance();

    private ValueEventListener mUserListener;
    private DAO dao;
    private String key;

    private final ValueEventListener userListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.i(TAG, "onDataChange: " + snapshot);
            userData.setValue(snapshot.getValue(User.class));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w(TAG, "onCancelled: ", error.toException());
        }
    };

    public LiveData<User> getUser(DAO dao, String key) {
        this.dao = dao;
        this.key = key;
        dao.addUserListener(userListener, key);
        mUserListener = userListener;
        return userData;
    }

    public LiveData<User> getUser() {
        return userData;
    }

    // For testing purposes
    public ValueEventListener getUserListener() {
        return mUserListener;
    }
    public void setUser(User user) {
        userData.setValue(user);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mUserListener != null) {
            dao.removeUserListener(userListener, key);
            mUserListener = null;
        }
    }

}
