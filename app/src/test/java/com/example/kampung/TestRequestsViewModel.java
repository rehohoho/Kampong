package com.example.kampung;


import static org.mockito.Mockito.mock;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.example.kampung.controllers.DAO;
import com.example.kampung.controllers.RequestsViewModel;
import com.example.kampung.models.Request;
import com.example.kampung.models.RequestAction;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.MockitoAnnotations;


@RunWith(JUnit4.class)
public class TestRequestsViewModel {

    @Rule // -> allows liveData to work on different thread besides main, must be public!
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    private DataSnapshot snapShot;

    private RequestsViewModel requestsViewModel;
    private LiveData<RequestAction> liveRequestAction;
    private ChildEventListener listener;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        DAO dao = mock(DAO.class);
        snapShot = mock(DataSnapshot.class);

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
    public void isLiveDataCorrect() throws InterruptedException {
        RequestAction testRequestAction = new RequestAction(new Request(), RequestAction.ACTION_ID.ADDED);
        requestsViewModel.setRequest(testRequestAction);
        assert LiveDataTestUtil.getOrAwaitValue(liveRequestAction) == testRequestAction;
    }

    @Test
    public void isLiveDataAdded() throws InterruptedException {
        listener.onChildAdded(snapShot, null);
        assert LiveDataTestUtil.getOrAwaitValue(liveRequestAction).getActionId() == RequestAction.ACTION_ID.ADDED;
    }

    @Test
    public void isLiveDataChanged() throws InterruptedException {
        listener.onChildChanged(snapShot, null);
        assert LiveDataTestUtil.getOrAwaitValue(liveRequestAction).getActionId() == RequestAction.ACTION_ID.CHANGED;
    }

    @Test
    public void isLiveDataMoved() throws InterruptedException {
        listener.onChildMoved(snapShot, null);
        assert LiveDataTestUtil.getOrAwaitValue(liveRequestAction).getActionId() == RequestAction.ACTION_ID.MOVED;
    }

    @Test
    public void isLiveDataRemoved() throws InterruptedException {
        listener.onChildRemoved(snapShot);
        assert LiveDataTestUtil.getOrAwaitValue(liveRequestAction).getActionId() == RequestAction.ACTION_ID.REMOVED;
    }

}
