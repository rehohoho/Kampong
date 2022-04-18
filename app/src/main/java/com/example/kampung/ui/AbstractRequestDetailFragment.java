package com.example.kampung.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kampung.R;
import com.example.kampung.controllers.UserViewModel;
import com.example.kampung.databinding.FragmentRequestDetailBinding;
import com.example.kampung.models.Request;


public abstract class AbstractRequestDetailFragment extends Fragment {

    private FragmentRequestDetailBinding binding;
    protected Request req;
    protected UserViewModel userViewModel;

    public AbstractRequestDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            req = bundle.getParcelable("request");
        }
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRequestDetailBinding.inflate(inflater, container, false);

        binding.reqdetailsLocation.setText(req.order.location);
        binding.reqdetailsDest.setText(req.dest);
        binding.reqdetailsOrderdetails.setText(req.order.food);
        binding.reqdetailsTelehandle.setText("@" + req.user.telegramHandle);
        binding.reqdetailsTime.setText(req.getTimeInString());
        binding.reqdetailsVendor.setText(req.order.vendor);
        binding.reqdetailsUser.setText("Posted by: " + req.user.username);

        if (req.isAccepted)
            binding.textAccepted.setText("Accepted by @" + req.acceptedBy.telegramHandle);
        else {
            binding.textAccepted.setText("Not accepted yet");
        }

        if (req.order.location.equals("SUTD Canteen")) {
            binding.locationImg.setImageResource(R.drawable.location_sutd);
        } else if (req.order.location.equals("Eastpoint Mall")) {
            binding.locationImg.setImageResource(R.drawable.location_eastpoint);
        } else if (req.order.location.equals("Changi City Point")) {
            binding.locationImg.setImageResource(R.drawable.location_ccp);
        }else if (req.order.location.equals("Bedok Point")) {
            binding.locationImg.setImageResource(R.drawable.location_bedokpoint);
        }else if (req.order.location.equals("Jewel Changi Airport")) {
            binding.locationImg.setImageResource(R.drawable.location_jewel);
        } else {
            binding.locationImg.setImageResource(R.drawable.location_simpang);
        }

        binding.reqActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonOnClick(view);
            }
        });

        return binding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onDestroyView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public abstract void buttonOnClick(View view);
}
