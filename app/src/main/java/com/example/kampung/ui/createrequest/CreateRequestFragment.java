package com.example.kampung.ui.createrequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.kampung.R;
import com.example.kampung.controllers.DAO;
import com.example.kampung.controllers.UserViewModel;
import com.example.kampung.models.Order;
import com.example.kampung.models.Request;
import com.example.kampung.models.User;


public class CreateRequestFragment extends Fragment {
    private Spinner locationlist, pickuplist, requestduelist;
    private SharedPreferences mPreferences;
    private UserViewModel userViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View reqView =inflater.inflate(R.layout.fragment_create_req, container, false);


        // for the options in each of the drop downs (not sure if thrs a btr way haha)
        String [] locations = {"SUTD Canteen", "Eastpoint Mall", "Changi City Point", "Simpang", "Jewel Changi Airport", "Bedok Point"};
        String [] pickuppoints = {"Blk 59 Hostel Lobby", "Blk 57 Hostel Lobby", "Building 1 Pickup Point"};
        String [] requestdues = {"12pm", "3pm", "6pm", "9pm"};


        locationlist = reqView.findViewById(R.id.reqlocation);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                locations);
        locationlist.setAdapter(adapter);

        pickuplist = reqView.findViewById(R.id.reqpickup);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                pickuppoints);
        pickuplist.setAdapter(adapter1);

        requestduelist = reqView.findViewById(R.id.reqdue);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                requestdues);
        requestduelist.setAdapter(adapter2);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        return reqView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText mUsername = getActivity().findViewById(R.id.reqname);

        mPreferences = getActivity().getSharedPreferences("com.example.kampung",Context.MODE_PRIVATE);
        mUsername.setText(mPreferences.getString(getString(R.string.username), ""));

        Button btnSubmit = (Button) getView().findViewById(R.id.createreq);
        EditText bOrder = (EditText) getView().findViewById(R.id.reqorderdeets);
        EditText bRestaurant = (EditText) getView().findViewById(R.id.reqrestaurant);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = userViewModel.getUser().getValue();
                Order order = new Order(
                    locationlist.getSelectedItem().toString(),
                    bRestaurant.getText().toString(),
                    bOrder.getText().toString(),
                    null, null
                );
                Request request = new Request(
                    user,
                    order,
                    null,
                    pickuplist.getSelectedItem().toString(),
                    false,
                    false,
                    null
                );

                Toast.makeText(getContext(),"Request created!", Toast.LENGTH_SHORT).show();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_activity);
                navController.navigate(R.id.action_navigation_create_request_to_navigation_home);
                DAO.getInstance().add(request);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}