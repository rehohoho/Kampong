package com.example.kampung.ui.userprofile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kampung.R;
import com.example.kampung.controllers.DAO;
import com.example.kampung.models.Request;
import com.example.kampung.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UserProfileFragment extends Fragment {

    private TextView mUserTeleHandleTextView;
    private TextView userNameTextView;
    private RecyclerView myRequestsRecyclerView;
    private List<Request> myRequests = new ArrayList<>();
    private List<String> myRequestKeys = new ArrayList<>();

    private DatabaseReference mDatabaseRootRef;
    private DatabaseReference requestNodeRef;

    private NavController navController;
    private SharedPreferences mSharedPreferences;

    private String userTeleHandle;
    private String userName;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        setDatabase();

        myRequestsRecyclerView = view.findViewById(R.id.my_requests);
        myRequestsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void onPause() {
        super.onPause();
        myRequests.clear();
    }


    private void setDatabase(){
        mDatabaseRootRef = FirebaseDatabase.getInstance().getReference();
        requestNodeRef = mDatabaseRootRef.child("Request");
        requestNodeRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Request req = snapshot.getValue(Request.class);
                String reqKey = snapshot.getKey();
                if (req.getUser().username.equals(userName) && req.getUser().telegramHandle.equals(userTeleHandle)){
                    myRequests.add(req);
                    myRequestKeys.add(reqKey);
                }
                myRequestsRecyclerView.setAdapter(new RequestAdapter(getContext(), myRequests,navController,myRequestKeys));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Request req = snapshot.getValue(Request.class);
                myRequests.add(req);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setUserProfile(View view){
        mSharedPreferences = getContext().getSharedPreferences("com.example.kampung", Context.MODE_PRIVATE);
        userTeleHandle = mSharedPreferences.getString(getString(R.string.userTeleHandle)," ");
        mUserTeleHandleTextView=view.findViewById(R.id.telegram_handle);
        mUserTeleHandleTextView.setText("@"+userTeleHandle);
        userNameTextView=view.findViewById(R.id.username);
        userName = mSharedPreferences.getString(getString(R.string.username),  " ");
        userNameTextView.setText(userName);
    }

}