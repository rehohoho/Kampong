package com.example.kampung.ui.createrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kampung.R;

public class CreateRequestFragment extends Fragment {
    private Spinner locationlist, pickuplist, requestduelist;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View reqView =inflater.inflate(R.layout.fragment_create_req, container, false);

        // for the options in each of the drop downs (not sure if thrs a btr way haha)
        String [] locations = {"SUTD Canteen", "Eastpoint Mall", "Changi City Point", "Simpang"};
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

        return reqView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}