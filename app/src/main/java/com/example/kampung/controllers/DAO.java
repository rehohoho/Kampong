package com.example.kampung.controllers;

import com.example.kampung.models.Request;
import com.example.kampung.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton implementation
 */
public class DAO {

    private final String TAG = "DAO";
    private final String DB_USER_ID = "senat";
    private static DAO dao;

    private DatabaseReference requestDb;
    private DatabaseReference userDb;

    private DAO()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        requestDb = db.getReference(Request.class.getSimpleName());
        userDb = db.getReference(User.class.getSimpleName());
    }

    public static DAO getInstance() {
        if (dao == null) {
            dao = new DAO();
        }
        return dao;
    }

    public Task<Void> addRequest(Request request) {
        String key = requestDb.push().getKey();
        Map<String, Object> requestUpdates = new HashMap<>();
        requestUpdates.put(key, request);
        return requestDb.updateChildren(requestUpdates);
    }

    public Task<Void> addUser(User user) {
        String key = requestDb.push().getKey();
        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put(key, user);
        return userDb.push().setValue(userUpdates);
    }

    public void addRequestsListener(ChildEventListener listener) {
        requestDb.addChildEventListener(listener);
    }

    public void removeRequestListener(ChildEventListener listener) {
        requestDb.removeEventListener(listener);
    }

    public void addUserListener(ValueEventListener listener) {
        userDb.addValueEventListener(listener);
    }

    public void removeUserListener(ValueEventListener listener) {
        userDb.removeEventListener(listener);
    }

}
