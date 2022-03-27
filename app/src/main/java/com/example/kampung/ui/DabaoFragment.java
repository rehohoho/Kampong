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
import com.example.kampung.models.Order;
import com.example.kampung.models.Request;
import com.example.kampung.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DabaoFragment extends Fragment {

    private final String TAG = "DabaoFragment";
    private final String DB_INSTANCE = "https://kampung-76142-default-rtdb.asia-southeast1.firebasedatabase.app";
    private final String DB_REQUEST_KEY = "requests";
    private final String DB_USER_ID = "senat";

    private FragmentDabaoBinding binding;
    private DatabaseReference mOrderReference;
    private ChildEventListener mOrderListener;

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
            .child(DB_REQUEST_KEY).child(DB_USER_ID);

        binding.buttonBack.setOnClickListener(view1 ->
            NavHostFragment.findNavController(DabaoFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment));
        binding.buttonDabao.setOnClickListener(view1 -> {
            submitRequest();
        });
    }

    public void submitRequest() {
        String key = mOrderReference.push().getKey();
        Order order = new Order("senat house", "senat kitchen", "varshini", true);
        User user = new User("senat", "senat");
        Request request = new Request(user, order, System.currentTimeMillis(), 0L, "SUTD", false, false);
        Map<String, Object> requestValues = request.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, requestValues);

        mOrderReference.updateChildren(childUpdates);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        ChildEventListener orderListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded: " + snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildChanged: " + snapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onChildRemoved: " + snapshot);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildRemoved: " + snapshot);
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

        if (mOrderListener != null) {
            mOrderReference.removeEventListener(mOrderListener);
        }
    }
}