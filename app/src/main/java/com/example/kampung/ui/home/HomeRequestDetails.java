package com.example.kampung.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.kampung.R;
import com.example.kampung.controllers.DAO;
import com.example.kampung.controllers.UserViewModel;
import com.example.kampung.databinding.FragmentHomeRequestDetailsBinding;
import com.example.kampung.models.Request;
import com.example.kampung.models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeRequestDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeRequestDetails extends Fragment {
    private FragmentHomeRequestDetailsBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Request req;
    User currUser;
    TextView location,vendor,time,telehandle,user, dest,food;
    Button dabao;

    public HomeRequestDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeRequestDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeRequestDetails newInstance(String param1, String param2) {
        HomeRequestDetails fragment = new HomeRequestDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        if (getArguments() != null) {

            req = bundle.getParcelable("request");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeRequestDetailsBinding.inflate(inflater, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.reqdetailsLocation.setText(req.order.location);
        binding.reqdetailsDest.setText(req.dest);
        binding.reqdetailsOrderdetails.setText(req.order.food);
        binding.reqdetailsTelehandle.setText(req.user.telegramHandle);
        binding.reqdetailsTime.setText(req.time.toString());
        binding.reqdetailsVendor.setText(req.order.vendor);
        binding.reqdetailsUser.setText(req.user.username);

        binding.dabaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "accepted", Toast.LENGTH_SHORT).show();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_activity_bottom_nav);
                navController.navigate(R.id.action_home_req_details_to_home);

            }
        });

        UserViewModel userViewModel = new UserViewModel();
        userViewModel.getUser(DAO.getInstance()).observe(getViewLifecycleOwner(), user -> {
            currUser = user;
        });

        return binding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onDestroyView();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}