package com.example.kampung.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.kampung.R;
import com.example.kampung.databinding.FragmentDabaoBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DabaoFragment extends Fragment {

    private final String TAG = "DabaoFragment";
    private final String DB_INSTANCE = "https://kampung-76142-default-rtdb.asia-southeast1.firebasedatabase.app";
    private final String DB_ORDER_KEY = "orders";
    private final String DB_USER_KEY = "users";
    private final String DB_USER_ID = "senat";

    private FragmentDabaoBinding binding;
    private DatabaseReference mOrderReference;
    private ChildEventListener mOrderListener;
    private DatabaseReference mUserReference;
    private ValueEventListener mUserListener;

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentDabaoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mOrderReference = FirebaseDatabase.getInstance(DB_INSTANCE).getReference()
            .child(DB_ORDER_KEY).child(DB_USER_ID);
        mUserReference = FirebaseDatabase.getInstance(DB_INSTANCE).getReference()
            .child(DB_USER_KEY).child(DB_USER_ID);

        binding.buttonBack.setOnClickListener(view1 ->
            NavHostFragment.findNavController(DabaoFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment));
        binding.buttonDabao.setOnClickListener(view1 -> {
            mOrderReference.push().setValue(String.valueOf(System.currentTimeMillis()));
            mUserReference.setValue("I am senat");
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String change = snapshot.getValue(String.class);
                Log.i(TAG, "onDataChange" + change);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onCancelled", error.toException());
            }
        };
        mUserReference.addValueEventListener(userListener);
        mUserListener = userListener;

        ChildEventListener orderListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String ts = snapshot.getValue(String.class);
                Log.d(TAG, "onChildAdded: " + snapshot.getKey() + ": " + ts);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String ts = snapshot.getValue(String.class);
                Log.d(TAG, "onChildChanged: " + snapshot.getKey() + ": " + ts);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String ts = snapshot.getValue(String.class);
                Log.d(TAG, "onChildRemoved: " + snapshot.getKey() + ": " + ts);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String ts = snapshot.getValue(String.class);
                Log.d(TAG, "onChildRemoved: " + snapshot.getKey() + ": " + ts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "onCancelled", error.toException());
            }
        };
        mOrderReference.addChildEventListener(orderListener);
        mOrderListener = orderListener;
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mUserListener != null) {
            mUserReference.removeEventListener(mUserListener);
        }
        if (mOrderListener != null) {
            mOrderReference.removeEventListener(mOrderListener);
        }
    }
}