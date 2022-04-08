package com.example.kampung;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.example.kampung.controllers.DAO;
import com.example.kampung.controllers.RequestsViewModel;
import com.example.kampung.models.Order;
import com.example.kampung.models.Request;
import com.example.kampung.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;


@RunWith(JUnit4.class)
public class TestRequestsViewModel {

    @Rule // -> allows liveData to work on different thread besides main, must be public!
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    private final String SNAPSHOT_KEY = "test";
    private final Request SNAPSHOT_VALUE = new Request();

    private DataSnapshot snapshot;
    private RequestsViewModel requestsViewModel;
    private LiveData<HashMap<String, Request>> liveRequestAction;
    private ChildEventListener listener;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        DAO dao = mock(DAO.class);
        snapshot = mock(DataSnapshot.class);
        when(snapshot.getKey()).thenReturn(SNAPSHOT_KEY);
        when(snapshot.getValue(Request.class)).thenReturn(SNAPSHOT_VALUE);

        requestsViewModel = new RequestsViewModel();
        liveRequestAction = requestsViewModel.getRequests(dao);
        listener = requestsViewModel.getRequestListener();
    }

    @Test
    public void isListenerAttached() {
        assert requestsViewModel.getRequestListener() != null;
    }

    @Test
    public void isListenerRemoved() throws InterruptedException {
        ViewModelTestUtil.callOnClear(requestsViewModel);
        assert requestsViewModel.getRequestListener() == null;
    }

    @Test
    public void isLiveDataAdded() throws InterruptedException {
        listener.onChildAdded(snapshot, null);
        HashMap<String, Request> res = LiveDataTestUtil.getOrAwaitValue(liveRequestAction);
        assert res.containsKey(SNAPSHOT_KEY);
        assert res.get(SNAPSHOT_KEY) == SNAPSHOT_VALUE;
    }

    @Test
    public void isLiveDataChanged() throws InterruptedException {
        Request updateRequest = new Request(new User(), new Order(), 0L, 0L, "", "", false);
        when(snapshot.getValue(Request.class)).thenReturn(updateRequest);

        listener.onChildChanged(snapshot, null);
        HashMap<String, Request> res = LiveDataTestUtil.getOrAwaitValue(liveRequestAction);
        assert res.containsKey(SNAPSHOT_KEY);
        assert res.get(SNAPSHOT_KEY) == updateRequest;
    }

    @Test
    public void isLiveDataRemoved() throws InterruptedException {
        listener.onChildRemoved(snapshot);
        HashMap<String, Request> res = LiveDataTestUtil.getOrAwaitValue(liveRequestAction);
        assert res.size() == 0;
    }

}
