package com.example.kampung;


import static org.mockito.Mockito.mock;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.example.kampung.controllers.DAO;
import com.example.kampung.controllers.RequestsViewModel;
import com.example.kampung.models.Request;
import com.example.kampung.models.RequestAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


@RunWith(JUnit4.class)
public class TestRequestsViewModel {

    @Rule // -> allows liveData to work on different thread besides main, must be public!
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    private RequestsViewModel requestsViewModel;
    private LiveData<RequestAction> liveRequestAction;

    @Mock
    DAO dao;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        dao = mock(DAO.class);
        requestsViewModel = new RequestsViewModel();
        liveRequestAction = requestsViewModel.getRequests(dao);
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
    public void isLiveDataEmitting() throws InterruptedException {
        RequestAction testRequestAction = new RequestAction(new Request(), RequestAction.ACTION_ID.ADDED);
        requestsViewModel.setRequest(testRequestAction);
        assert LiveDataTestUtil.getOrAwaitValue(liveRequestAction) == testRequestAction;
    }

}
