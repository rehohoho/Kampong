package com.example.kampung.controllers;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kampung.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


/**
 * ViewModel helps prepare data for UI, retained during configuration changes,
 * available to next fragment / activity instance.
 *
 * Singleton implementation
 */
public class UserViewModel extends ViewModel {

    private final String TAG = "UserViewModel";
    private MutableLiveData<User> userData;

    private ValueEventListener mUserListener;
    private DAO dao;

    private final ValueEventListener userListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Log.i(TAG, "onDataChange: " + snapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w(TAG, "onCancelled: ", error.toException());
        }

        public Boolean checkIfExists(DataSnapshot snapshot) {
            return snapshot.exists();
        }
    };

    public LiveData<User> getUser(DAO dao) {
        if (userData == null) {
            this.dao = dao;
            dao.addUserListener(userListener);
            mUserListener = userListener;
            userData = new MutableLiveData<>();
        }
        return userData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mUserListener != null) {
            dao.removeUserListener(userListener);
        }
    }


}
