package com.example.kampung;

import com.example.kampung.databinding.FragmentDabaoBinding;
import com.example.kampung.models.Request;
import com.example.kampung.models.SmolRequest;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAORequest {

    private final String TAG = "DabaoFragment";
    private final String DB_INSTANCE = "https://kampung-76142-default-rtdb.asia-southeast1.firebasedatabase.app";
    private final String DB_REQUEST_KEY = "requests";
    private final String DB_USER_ID = "senat";

    private FragmentDabaoBinding binding;
    private DatabaseReference databaseReference;
    private ChildEventListener mRequestListener;

    public DAORequest()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Request.class.getSimpleName());
    }

    public Task<Void> add(SmolRequest req){
        return databaseReference.push().setValue(req);
    }

}
