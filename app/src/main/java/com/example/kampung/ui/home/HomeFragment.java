package com.example.kampung.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.kampung.R;
import com.example.kampung.controllers.DAO;
import com.example.kampung.controllers.RequestsViewModel;
import com.example.kampung.databinding.FragmentHomeBinding;
import com.example.kampung.models.RequestAction;
import com.example.kampung.ui.search.SearchActivity;
import com.example.kampung.ui.search.SearchRequestAdapter;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class HomeFragment extends Fragment {

    private final String TAG = "HomeFragment";
    private RequestsViewModel requestsViewModel;
    private ArrayList<RequestAction> requestList = new ArrayList<>();
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView text = ((TextView) view.findViewById(R.id.text_request));

        if (SearchActivity.query.length() > 0) {
            Log.d(TAG, "View Created " + SearchActivity.query + " queries");
            SearchRequestAdapter requestAdapter = new SearchRequestAdapter(getContext(), requestList);
            binding.recyclerBrowsereq.setAdapter(requestAdapter);
            binding.recyclerBrowsereq.setLayoutManager(new LinearLayoutManager(getContext()));
            requestsViewModel = new ViewModelProvider(this).get(RequestsViewModel.class);
            requestsViewModel.getRequests(DAO.getInstance()).observe(getViewLifecycleOwner(), request -> {
                if (request.getRequest().order.location.equals(SearchActivity.query)) {
                    requestList.add(request);
                    requestAdapter.notifyItemInserted(requestList.size() - 1);
                }
                // DISPLAY TOASTS FOR SUCCESSFUL / UNSUCCESSFUL SEARCHES
                if (requestList.isEmpty()) {
                    text.setText("No requests were found.");

                    Toast.makeText(getActivity(), "No requests with " + SearchActivity.query + " was found.", Toast.LENGTH_SHORT).show();
                }
                else {
                    text.setText("Filter-" + SearchActivity.query);
                    Toast.makeText(getActivity(), "Displaying " + SearchActivity.query + " queries", Toast.LENGTH_SHORT).show();
                }
                // RESET QUERY FROM SEARCHACTIVITY
                SearchActivity.query = "";
            });
        }
        else {
            Log.d(TAG, "No Search");
            HomeRequestAdapter requestAdapter = new HomeRequestAdapter(getContext(), requestList);
            binding.recyclerBrowsereq.setAdapter(requestAdapter);
            binding.recyclerBrowsereq.setLayoutManager(new LinearLayoutManager(getContext()));

            requestsViewModel = new ViewModelProvider(this).get(RequestsViewModel.class);
            requestsViewModel.getRequests(DAO.getInstance()).observe(getViewLifecycleOwner(), request -> {
                if(!request.getRequest().isDelivered && request.getActionId() == RequestAction.ACTION_ID.ADDED){
                    requestList.add(request);
                    requestAdapter.notifyItemInserted(requestList.size() - 1);
                }
            });
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}