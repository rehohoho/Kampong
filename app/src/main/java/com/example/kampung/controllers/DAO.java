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
    private static DAO dao;

    private DatabaseReference requestDb;
    private DatabaseReference userDb;
    private FirebaseDatabase db;

    private DAO() {
        db = FirebaseDatabase.getInstance();
        requestDb = db.getReference(Request.class.getSimpleName());
        userDb = db.getReference(User.class.getSimpleName());
    }

    public static DAO getInstance() {
        if (dao == null) {
            dao = new DAO();
        }
        return dao;
    }

    public String add(Object value) {
        DatabaseReference dbRef = db.getReference(value.getClass().getSimpleName());
        String key = dbRef.push().getKey();
        Map<String, Object> updates = new HashMap<>();
        updates.put(key, value);
        dbRef.updateChildren(updates);
        return key;
    }

    public Task<Void> update(Object value, String key) {
        DatabaseReference dbRef = db.getReference(value.getClass().getSimpleName());
        String keyStr = key.toString();
        Map<String, Object> updates = new HashMap<>();
        updates.put(keyStr, value);
        return dbRef.updateChildren(updates);
    }

    public void remove(String path, String key){
        DatabaseReference dbRef = db.getReference(path);
        dbRef.child(key).removeValue();
    }

    public void addRequestsListener(ChildEventListener listener) {
        requestDb.addChildEventListener(listener);
    }

    public void removeRequestListener(ChildEventListener listener) {
        requestDb.removeEventListener(listener);
    }

    public void addUserListener(ValueEventListener listener, String key) {
        userDb.child(key).addValueEventListener(listener);
    }

    public void removeUserListener(ValueEventListener listener, String key) {
        userDb.child(key).removeEventListener(listener);
    }

}
