package com.example.kampung.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.kampung.R;
import com.example.kampung.controllers.DAO;
import com.example.kampung.databinding.FragmentRequestDetailBinding;
import com.example.kampung.models.Request;


public class RequestDetailFragment extends Fragment {

    private FragmentRequestDetailBinding binding;
    private Request mRequest;
    private Button confirmButton;

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
        Bundle bundle = getArguments();
        if (bundle != null) {
            mRequest = bundle.getParcelable("request");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRequestDetailBinding.inflate(inflater, container, false);
        binding.reqdetailsOrderdetails.setText(mRequest.order.food);
        binding.reqdetailsLocation.setText(mRequest.order.location);
        binding.reqdetailsVendor.setText(mRequest.order.vendor);
        binding.reqdetailsUser.setText("posted by: " + mRequest.user.username);
        binding.reqdetailsTelehandle.setText("@" + mRequest.user.telegramHandle);
        binding.reqdetailsDest.setText(mRequest.dest);
        binding.reqdetailsTime.setText(mRequest.getTimeInString());

        if (mRequest.isAccepted)
            binding.textAccepted.setText("@" + mRequest.acceptedBy.telegramHandle);
        else {
            binding.textAccepted.setText("No one accept");
        }

        if (mRequest.order.location.equals("SUTD Canteen")) {
            binding.locationImg.setImageResource(R.drawable.location_sutd);
        } else if (mRequest.order.location.equals("Eastpoint Mall")) {
            binding.locationImg.setImageResource(R.drawable.location_eastpoint);
        } else if (mRequest.order.location.equals("Changi City Point")) {
            binding.locationImg.setImageResource(R.drawable.location_ccp);
        }else if (mRequest.order.location.equals("Bedok Point")) {
            binding.locationImg.setImageResource(R.drawable.location_bedokpoint);
        }else if (mRequest.order.location.equals("Jewel Changi Airport")) {
            binding.locationImg.setImageResource(R.drawable.location_jewel);
        } else {
            binding.locationImg.setImageResource(R.drawable.location_simpang);
        }

        setConfirmButton();

        return binding.getRoot();
    }

    private void setConfirmButton(){
        confirmButton = binding.deliveredButton;
        if (!mRequest.isAccepted) {
            confirmButton.setText("Remove Request");
        } else {
            confirmButton.setText("Delivered");
        }
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DAO dao = DAO.getInstance();
                dao.remove("Request", mRequest.uniqueID);
                Toast.makeText(getContext(), "Delivery confirmed", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_requestDetailFragment_to_navigation_user_profile);
            }
        });
    }
}