package com.example.kampung.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.kampung.R;
import com.example.kampung.controllers.RequestsViewModel;
import com.example.kampung.databinding.FragmentMainBinding;
import com.example.kampung.models.Request;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    private final String TAG = "MainFragment";
    private FragmentMainBinding binding;

    private RequestsViewModel requestsViewModel;
    private ArrayList<Request> requestList = new ArrayList<>();

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RequestAdapter requestAdapter = new RequestAdapter(getContext(), requestList);
        binding.mainRecyclerView.setAdapter(requestAdapter);
        binding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        requestsViewModel = new ViewModelProvider(this).get(RequestsViewModel.class);
        requestsViewModel.getRequests(DAO.getInstance()).observe(getViewLifecycleOwner(), request -> {
            Log.d(TAG, "nani " + request.toString());
            requestList.add(request);
            requestAdapter.notifyItemInserted(requestList.size() - 1);
        });

        // handles navigation to Dabao Fragment
        binding.buttonOrder.setOnClickListener(view1 ->
            NavHostFragment.findNavController(MainFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}