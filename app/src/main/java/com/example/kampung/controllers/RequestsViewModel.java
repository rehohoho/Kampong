package com.example.kampung.controllers;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kampung.models.Request;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


public class RequestsViewModel extends ViewModel {

    private static final String TAG = "RequestsViewModel";
    private MutableLiveData<Request> requestData;
    private ChildEventListener mRequestListener;

    private final ChildEventListener requestListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Log.d(TAG, "onChildAdded: " + snapshot);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Log.d(TAG, "onChildChanged: " + snapshot);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            Log.d(TAG, "onChildRemoved: " + snapshot);
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Log.d(TAG, "onChildRemoved: " + snapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w(TAG, "onCancelled", error.toException());
        }
    };

    private RequestsViewModel() {
    }

    public LiveData<Request> getRequests() {
        if (requestData == null) {
            DAO.getInstance().addRequestsListener(requestListener);
            mRequestListener = requestListener;
            requestData = new MutableLiveData<>();
            loadRequests();
        }
        return requestData;
    }

    private void loadRequests() {
        // asynchronously load requestData here
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRequestListener != null) {
            DAO.getInstance().removeRequestListener(mRequestListener);
        }
    }

}
