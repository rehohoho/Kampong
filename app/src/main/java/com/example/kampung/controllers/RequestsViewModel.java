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
    private DAO dao;

    private final ChildEventListener requestListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Log.d(TAG, "onChildAdded: " + snapshot);
            requestData.setValue(snapshot.getValue(Request.class));
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

    public LiveData<Request> getRequests(DAO dao) {
        if (requestData == null) {
            this.dao = dao;
            dao.addRequestsListener(requestListener);
            mRequestListener = requestListener;
            requestData = new MutableLiveData<>();
        }
        return requestData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRequestListener != null) {
            dao.removeRequestListener(mRequestListener);
        }
    }

}
