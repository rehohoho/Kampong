package com.example.kampung.ui.userprofile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kampung.R;
import com.example.kampung.controllers.DAO;
import com.example.kampung.controllers.RequestsViewModel;
import com.example.kampung.models.Request;
import com.example.kampung.ui.RequestAdapter;

import java.util.ArrayList;


public class UserProfileFragment extends Fragment {

    private TextView mUserTeleHandleTextView;
    private TextView userNameTextView;
    private ArrayList<Request> myRequests = new ArrayList<>();

    private NavController navController;
    private SharedPreferences mSharedPreferences;

    private String userTeleHandle;
    private String userName;

    private RequestsViewModel requestsViewModel;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);
        setUserProfile(view);

        RequestAdapter requestAdapter = new RequestAdapter(getContext(), myRequests, R.id.action_navigation_user_profile_to_requestDetailFragment);
        RecyclerView myRequestsRecyclerView = view.findViewById(R.id.my_requests);
        myRequestsRecyclerView.setAdapter(requestAdapter);
        myRequestsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        requestsViewModel = new ViewModelProvider(this).get(RequestsViewModel.class);
        requestsViewModel.getRequests(DAO.getInstance()).observe(getViewLifecycleOwner(), requestAction -> {
            Request req = requestAction.getRequest();
            if (!myRequests.contains(req) && (
                req.user.telegramHandle.equals(userTeleHandle) ||
                (req.acceptedBy != null && req.acceptedBy.telegramHandle.equals(userTeleHandle))
            )) {
                myRequests.add(req);
                requestAdapter.notifyItemInserted(myRequests.size() - 1);
            }
        });
    }

    private void setUserProfile(View view){
        mSharedPreferences = getContext().getSharedPreferences("com.example.kampung", Context.MODE_PRIVATE);
        userTeleHandle = mSharedPreferences.getString(getString(R.string.package_name)," ");
        userName = mSharedPreferences.getString(getString(R.string.username),  " ");

        mUserTeleHandleTextView = view.findViewById(R.id.telegram_handle);
        mUserTeleHandleTextView.setText("@"+userTeleHandle);
        userNameTextView = view.findViewById(R.id.username);
        userNameTextView.setText(userName);
    }

}