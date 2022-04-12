package com.example.kampung.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kampung.R;
import com.example.kampung.controllers.DAO;
import com.example.kampung.models.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestDetailFragment extends Fragment {

    private DatabaseReference dbRootRef;
    private DatabaseReference reqNodRef;
    private Request mRequest;
    private Button confirmButton;
    private String requestKey;

    private TextView orderDetailTextView;
    private TextView requestLocationTextView;
    private TextView vendorTextView;
    private TextView userTextView;
    private TextView teleHandleTextView;
    private TextView destTextView;
    private TextView acceptedByTextView;
    private TextView timeTextView;

    public RequestDetailFragment() {
        // Required empty public constructor
    }

    public static RequestDetailFragment newInstance(String param1, String param2) {
        RequestDetailFragment fragment = new RequestDetailFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        setDatabase();
        Looper looper = Looper.getMainLooper();
        Handler handler = new Handler(looper);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                requestKey = getArguments().getString("requestKey");
                Log.i("requestKey is",requestKey);
                reqNodRef.child(requestKey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Request request = snapshot.getValue(Request.class);
                        mRequest = request;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                setViews(view);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void setDatabase(){
        dbRootRef = FirebaseDatabase.getInstance().getReference();
        reqNodRef = dbRootRef.child("Request");
    }

    private void bindViews(View view){
        orderDetailTextView = view.findViewById(R.id.reqdetails_orderdetails);
        requestLocationTextView = view.findViewById(R.id.reqdetails_location);
        vendorTextView = view.findViewById(R.id.reqdetails_vendor);
        userTextView = view.findViewById(R.id.reqdetails_user);
        teleHandleTextView = view.findViewById(R.id.reqdetails_telehandle);
        destTextView = view.findViewById(R.id.reqdetails_dest);
        acceptedByTextView = view.findViewById(R.id.acceptedUser);
        timeTextView = view.findViewById(R.id.reqdetails_time);
    }

    private void setViews(View view) {
        if (mRequest == null) {
            Log.d("TAG", "Request not their");
        } else{
            orderDetailTextView.setText(mRequest.order.food);
            requestLocationTextView.setText(mRequest.order.location);
            vendorTextView.setText(mRequest.order.vendor);
            userTextView.setText("posted by: " + mRequest.user.username);
            teleHandleTextView.setText("@" + mRequest.user.telegramHandle);
            destTextView.setText(mRequest.dest);
            if (mRequest.isAccepted)
                acceptedByTextView.setText(mRequest.acceptedBy.username);
            else {
                TextView t = view.findViewById(R.id.text_accepted);
                t.setText("No one accept");
            }

            timeTextView.setText(mRequest.getTimeInString());
            setConfirmButton(view);

        }
    }

    private void setConfirmButton(View view){
        confirmButton = view.findViewById(R.id.delivered_button);
        if (mRequest.isDelivered) {
            confirmButton.setEnabled(false);
            confirmButton.setText(R.string.is_delivered);

        }else {
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DAO dao = DAO.getInstance();
                    dao.remove("Request", requestKey);


                    Toast.makeText(getContext(), "Delivery confirmed", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_requestDetailFragment_to_navigation_user_profile);
//                    reqNodRef.child(requestKey).child("isDelivered").setValue(true);
//                    Toast.makeText(getContext(), "Delivery confirmed", Toast.LENGTH_SHORT).show();
//                    Navigation.findNavController(view).navigate(R.id.action_requestDetailFragment_to_navigation_user_profile);
                }
            });
        }
    }
}