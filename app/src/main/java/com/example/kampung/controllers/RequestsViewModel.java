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


public class RequestsViewModel extends ViewModel {

    private static final String TAG = "RequestsViewModel";
    private MutableLiveData<RequestAction> requestData;
    private ChildEventListener mRequestListener;
    private DAO dao;

    private final ChildEventListener requestListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Log.d(TAG, "onChildAdded: " + snapshot);
            Request req = snapshot.getValue(Request.class);
            if (req != null) {
                req.uniqueID = snapshot.getKey();
            }
            requestData.setValue(new RequestAction(req, RequestAction.ACTION_ID.ADDED));
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Log.d(TAG, "onChildChanged: " + snapshot);
            Request req = snapshot.getValue(Request.class);
            requestData.setValue(new RequestAction(req, RequestAction.ACTION_ID.CHANGED));
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            Log.d(TAG, "onChildRemoved: " + snapshot);
            Request req = snapshot.getValue(Request.class);
            requestData.setValue(new RequestAction(req, RequestAction.ACTION_ID.REMOVED));
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Log.d(TAG, "onChildRemoved: " + snapshot);
            Request req = snapshot.getValue(Request.class);
            requestData.setValue(new RequestAction(req, RequestAction.ACTION_ID.MOVED));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w(TAG, "onCancelled", error.toException());
        }
    };

    public LiveData<RequestAction> getRequests(DAO dao) {
        if (requestData == null) {
            this.dao = dao;
            dao.addRequestsListener(requestListener);
            mRequestListener = requestListener;
            requestData = new MutableLiveData<>();
        }
        return requestData;
    }

    // For testing purposes
    public ChildEventListener getRequestListener() {
        return mRequestListener;
    }
    public void setRequest(RequestAction requestAction) {
        requestData.setValue(requestAction);
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
