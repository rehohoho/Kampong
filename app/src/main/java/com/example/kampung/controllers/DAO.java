package com.example.kampung.controllers;

import com.example.kampung.models.Order;
import com.example.kampung.models.Request;
import com.example.kampung.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public Task<Void> addRequest() {
        String key = requestDb.push().getKey();
        Order order = new Order("senat house", "senat kitchen", "varshini", true);
        User user = new User("senat", "senat");
        Request request = new Request(user, order, System.currentTimeMillis(), 0L, "SUTD", false, false);
        Map<String, Object> requestValues = request.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, requestValues);

        return requestDb.updateChildren(childUpdates);
    }

    public void addRequestsListener(ChildEventListener listener) {
        requestDb.addChildEventListener(listener);
    }

    public void removeRequestListener(ChildEventListener listener) {
        requestDb.removeEventListener(listener);
    }

}
