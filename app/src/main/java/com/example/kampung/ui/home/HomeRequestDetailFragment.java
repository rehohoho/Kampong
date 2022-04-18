package com.example.kampung.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.kampung.R;
import com.example.kampung.controllers.DAO;
import com.example.kampung.models.User;
import com.example.kampung.ui.AbstractRequestDetailFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeRequestDetailFragment extends AbstractRequestDetailFragment {

    public HomeRequestDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Button actionButton = view.findViewById(R.id.req_action_button);
        actionButton.setText("Dabao");
        return view;
    }

    public void buttonOnClick(View view) {
        User curruser = userViewModel.getUser().getValue();
        if(!curruser.telegramHandle.equals(req.user.telegramHandle)) {
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

}