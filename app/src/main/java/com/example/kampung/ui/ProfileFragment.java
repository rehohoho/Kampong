package com.example.kampung.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kampung.databinding.FragmentProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {
    private final String TAG = "ProfileFragment";
    private final String DB_INSTANCE = "https://kampung-76142-default-rtdb.asia-southeast1.firebasedatabase.app";
    private final String DB_USER_KEY = "users";
    private final String DB_USER_ID = "senat";

    private FragmentProfileBinding binding;
    private DatabaseReference mUserReference;
    private ValueEventListener mUserListener;

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserReference = FirebaseDatabase.getInstance(DB_INSTANCE).getReference()
            .child(DB_USER_KEY).child(DB_USER_ID);
    }

    @Override
    public void onStart() {
        super.onStart();

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i(TAG, "onDataChange: " + snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onCancelled: ", error.toException());
            }
        };
        mUserReference.addValueEventListener(userListener);
        mUserListener = userListener;
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mUserListener != null) {
            mUserReference.removeEventListener(mUserListener);
        }
    }
}
