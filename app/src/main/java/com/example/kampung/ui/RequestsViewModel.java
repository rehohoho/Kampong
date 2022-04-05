package com.example.kampung.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kampung.models.Request;


public class RequestsViewModel extends ViewModel {

    private MutableLiveData<Request> requestData;

    private RequestsViewModel() {

    }

    private LiveData<Request> getUser() {
        if (requestData == null) {
            requestData = new MutableLiveData<>();
            loadRequests();
        }
        return requestData;
    }

    private void loadRequests() {
        // asynchronously load requestData here
    }

}
