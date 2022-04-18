package com.example.kampung.ui.userprofile;

import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.kampung.R;
import com.example.kampung.controllers.DAO;
import com.example.kampung.ui.AbstractRequestDetailFragment;


public class UserRequestDetailFragment extends AbstractRequestDetailFragment {

    public UserRequestDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Button actionButton = view.findViewById(R.id.req_action_button);
        if (!req.isAccepted) {
            actionButton.setText("Remove Request");
        } else {
            actionButton.setText("Delivered");
        }
        return view;
    }

    @Override
    public void buttonOnClick(View view) {
        DAO dao = DAO.getInstance();
        dao.remove("Request", req.uniqueID);
        if (!req.isAccepted) {
            Toast.makeText(getContext(), "Request removed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Delivery confirmed", Toast.LENGTH_SHORT).show();
        }
        Navigation.findNavController(view).navigate(R.id.action_navigation_req_detail_to_user_profile);
    }
}