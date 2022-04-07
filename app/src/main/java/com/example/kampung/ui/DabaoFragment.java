package com.example.kampung.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.kampung.controllers.DAO;
import com.example.kampung.R;
import com.example.kampung.databinding.FragmentDabaoBinding;
import com.example.kampung.models.Order;
import com.example.kampung.models.Request;
import com.example.kampung.models.User;

import java.util.Map;


public class DabaoFragment extends Fragment {

    private final String TAG = "DabaoFragment";
    private FragmentDabaoBinding binding;

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = FragmentDabaoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO: please replace this with ui elements (input the text instead of hardcoding)
        Order order = new Order("senat house", "senat kitchen", "varshini", true);
        User user = new User("senat", "senat");
        Request request = new Request(user, order, System.currentTimeMillis(), 0L, "SUTD", false, false);

        // Code block that handles actions when submit button is clicked
        binding.btnSubmit.setOnClickListener(v -> {
            DAO.getInstance().addRequest(request).addOnSuccessListener(suc ->
            {
                Toast.makeText(getActivity(), "Record is inserted", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er ->
            {
                Toast.makeText(getActivity(), ""+er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });

        // handles navigation back to Main fragment
        binding.buttonBack.setOnClickListener(view1 ->
            NavHostFragment.findNavController(DabaoFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}