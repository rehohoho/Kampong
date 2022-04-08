package com.example.kampung.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kampung.controllers.DAO;
import com.example.kampung.controllers.UserViewModel;
import com.example.kampung.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

    private final String TAG = "ProfileFragment";
    private FragmentProfileBinding binding;
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState
    ) {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUser(DAO.getInstance()).observe(getViewLifecycleOwner(), user -> {
            Log.i(TAG, "Nice.");
        });
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
