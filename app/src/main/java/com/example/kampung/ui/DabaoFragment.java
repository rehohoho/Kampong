package com.example.kampung.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.kampung.DAORequest;
import com.example.kampung.MainActivity;
import com.example.kampung.R;
import com.example.kampung.databinding.FragmentDabaoBinding;
import com.example.kampung.models.Order;
import com.example.kampung.models.Request;
import com.example.kampung.models.SmolRequest;
import com.example.kampung.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DabaoFragment extends Fragment {

    private final String TAG = "DabaoFragment";
    private final String DB_INSTANCE = "https://kampung-76142-default-rtdb.asia-southeast1.firebasedatabase.app";
    private final String DB_REQUEST_KEY = "requests";
    private final String DB_USER_ID = "senat";

    private FragmentDabaoBinding binding;
    private DatabaseReference mRequestReference;

    // TODO: REQUESTLISTENER
    private ChildEventListener mRequestListener;

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

        EditText edit_username = getView().findViewById(R.id.edit_username);
        EditText edit_order = getView().findViewById(R.id.edit_order);
        Button submitBtn = getView().findViewById(R.id.btn_submit);


        DAORequest daoRequest = new DAORequest();

        // Code block that handles actions when submit button is clicked
        submitBtn.setOnClickListener(v -> {
            SmolRequest req = new SmolRequest(edit_username.getText().toString(), edit_order.getText().toString());
            daoRequest.add(req).addOnSuccessListener(suc ->
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

//    public void submitRequest() {
//        String key = mRequestReference.push().getKey();
//        Order order = new Order("senat house", "senat kitchen", "varshini", true);
//        User user = new User("senat", "senat");
//        Request request = new Request(user, order, System.currentTimeMillis(), 0L, "SUTD", false, false);
//            Map<String, Object> requestValues = request.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put(key, requestValues);
//
//        mRequestReference.updateChildren(childUpdates);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}