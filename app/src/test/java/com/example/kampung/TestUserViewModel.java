package com.example.kampung;


import static org.mockito.Mockito.mock;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.example.kampung.controllers.DAO;
import com.example.kampung.controllers.UserViewModel;
import com.example.kampung.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.MockitoAnnotations;


@RunWith(JUnit4.class)
public class TestUserViewModel {

    @Rule // -> allows liveData to work on different thread besides main, must be public!
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    private DataSnapshot snapShot;

    private UserViewModel usersViewModel;
    private LiveData<User> liveUser;
    private ValueEventListener listener;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        DAO dao = mock(DAO.class);
        snapShot = mock(DataSnapshot.class);

        usersViewModel = new UserViewModel();
        liveUser = usersViewModel.getUser(dao);
        listener = usersViewModel.getUserListener();
    }

    @Test
    public void isListenerAttached() {
        assert usersViewModel.getUserListener() != null;
    }

    @Test
    public void isListenerRemoved() throws InterruptedException {
        ViewModelTestUtil.callOnClear(usersViewModel);
        assert usersViewModel.getUserListener() == null;
    }

    @Test
    public void isLiveDataCorrect() throws InterruptedException {
        User user = new User();
        usersViewModel.setUser(user);
        assert LiveDataTestUtil.getOrAwaitValue(liveUser) == user;
    }

    @Test
    public void isLiveDataChanged() throws InterruptedException {
        listener.onDataChange(snapShot);
        assert LiveDataTestUtil.getOrAwaitValue(liveUser) == snapShot.getValue();
    }

}
