package com.example.kampung.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.kampung.R;
import com.example.kampung.controllers.DAO;
import com.example.kampung.controllers.RequestsViewModel;
import com.example.kampung.databinding.FragmentHomeBinding;
import com.example.kampung.models.RequestAction;
import com.example.kampung.ui.search.SearchActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private final String TAG = "HomeFragment";
    private RequestsViewModel requestsViewModel;
    private ArrayList<RequestAction> requestList = new ArrayList<>();
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        binding.icSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_activity_bottom_nav);
                navController.navigate(R.id.action_navigation_home_to_search_fragment);
            }
        });

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String searchString = SearchActivity.query;

        HomeRequestAdapter requestAdapter = new HomeRequestAdapter(getContext(), requestList);
        binding.recyclerBrowsereq.setAdapter(requestAdapter);
        binding.recyclerBrowsereq.setLayoutManager(new LinearLayoutManager(getContext()));
        requestsViewModel = new ViewModelProvider(this).get(RequestsViewModel.class);

        if (searchString.length() > 0) {
            Log.d(TAG, "View Created " + searchString + " queries");
            binding.textRequest.setText("Filtered Result");
            requestsViewModel.getRequests(DAO.getInstance()).observe(getViewLifecycleOwner(), request -> {
                if (request.getRequest().order.location.equals(searchString) && !request.getRequest().isAccepted && request.getActionId() == RequestAction.ACTION_ID.ADDED) {
                    requestList.add(request);
                    requestAdapter.notifyItemInserted(requestList.size() - 1);
                }
                // DISPLAY MESSAGE FOR SUCCESSFUL / UNSUCCESSFUL SEARCHES
                if (requestList.isEmpty()) {
                    binding.recyclerBrowsereq.setVisibility(View.GONE);
                    binding.emptyView.setText("No request found");
                    binding.emptyView.setVisibility(View.VISIBLE);
                } else {
                    binding.recyclerBrowsereq.setVisibility(View.VISIBLE);
                    binding.emptyView.setVisibility(View.GONE);
                }

            });
            // RESET QUERY FROM SEARCHACTIVITY
            SearchActivity.query = "";
        }
        else {
            Log.d(TAG, "No Search");
            requestsViewModel.getRequests(DAO.getInstance()).observe(getViewLifecycleOwner(), request -> {
                if(!request.getRequest().isAccepted && request.getActionId() == RequestAction.ACTION_ID.ADDED){
                    requestList.add(request);
                    requestAdapter.notifyItemInserted(requestList.size() - 1);
                }

                if (requestList.isEmpty()) {
                    binding.recyclerBrowsereq.setVisibility(View.GONE);
                    binding.emptyView.setText("No request available");
                    binding.emptyView.setVisibility(View.VISIBLE);
                } else {
                    binding.recyclerBrowsereq.setVisibility(View.VISIBLE);
                    binding.emptyView.setVisibility(View.GONE);
                }


            });
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}