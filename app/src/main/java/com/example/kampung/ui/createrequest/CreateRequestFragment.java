package com.example.kampung.ui.createrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kampung.R;

public class CreateRequestFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View dashboardView =inflater.inflate(R.layout.fragment_create_req, container, false);
        return dashboardView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}