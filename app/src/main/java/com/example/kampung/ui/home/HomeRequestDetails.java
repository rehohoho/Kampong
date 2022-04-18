package com.example.kampung.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
 */
public class HomeRequestDetails extends Fragment {
    private FragmentHomeRequestDetailsBinding binding;
    private UserViewModel userViewModel;

    Request req;

    public HomeRequestDetails() {
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
        binding = FragmentHomeRequestDetailsBinding.inflate(inflater, container, false);

        binding.reqdetailsLocation.setText(req.order.location);
        binding.reqdetailsDest.setText(req.dest);
        binding.reqdetailsOrderdetails.setText(req.order.food);
        binding.reqdetailsTelehandle.setText("@"+req.user.telegramHandle);
        binding.reqdetailsTime.setText(req.getTimeInString());
        binding.reqdetailsVendor.setText(req.order.vendor);
        binding.reqdetailsUser.setText("Posted by: "+req.user.username);
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

        binding.dabaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User curruser = userViewModel.getUser().getValue();
                if(!curruser.telegramHandle.equals(req.user.telegramHandle)){
                    Toast.makeText(getContext(),"Request accepted",Toast.LENGTH_SHORT).show();
                    req.isAccepted = true;
                    req.acceptedBy = curruser;
                    DAO dao = DAO.getInstance();
                    dao.update(req, req.uniqueID);
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_activity_bottom_nav);
                    navController.navigate(R.id.action_home_req_details_to_home);
                } else {
                    Toast.makeText(getContext(),"Cannot accept own request",Toast.LENGTH_SHORT).show();
                }
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

}