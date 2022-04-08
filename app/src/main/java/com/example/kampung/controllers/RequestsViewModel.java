package com.example.kampung.controllers;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kampung.models.Request;
import com.example.kampung.models.RequestAction;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.HashMap;


public class RequestsViewModel extends ViewModel {

    private static final String TAG = "RequestsViewModel";
    private MutableLiveData<HashMap<String, Request>> requestData;
    private HashMap<String, Request> requests;
    private ChildEventListener mRequestListener;
    private DAO dao;

    private final ChildEventListener requestListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Log.d(TAG, "onChildAdded: " + snapshot);
            requests.put(snapshot.getKey(), snapshot.getValue(Request.class));
            requestData.setValue(requests);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Log.d(TAG, "onChildChanged: " + snapshot);
            requests.put(snapshot.getKey(), snapshot.getValue(Request.class));
            requestData.setValue(requests);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            Log.d(TAG, "onChildRemoved: " + snapshot);
            requests.remove(snapshot.getKey());
            requestData.setValue(requests);
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Log.d(TAG, "onChildMoved: " + snapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w(TAG, "onCancelled", error.toException());
        }
    };

    public LiveData<HashMap<String, Request>> getRequests(DAO dao) {
        if (requestData == null) {
            this.dao = dao;
            dao.addRequestsListener(requestListener);
            mRequestListener = requestListener;
            requestData = new MutableLiveData<>();
            requests = new HashMap<>();
        }
        return requestData;
    }

    // For testing purposes
    public ChildEventListener getRequestListener() {
        return mRequestListener;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRequestListener != null) {
            dao.removeRequestListener(mRequestListener);
            mRequestListener = null;
        }
    }

}
