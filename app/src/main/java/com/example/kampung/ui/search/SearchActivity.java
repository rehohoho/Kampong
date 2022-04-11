package com.example.kampung.ui.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kampung.BottomNavActivity;
import com.example.kampung.R;
import com.example.kampung.models.Request;
import com.example.kampung.models.RequestAction;
import com.example.kampung.utility.NetworkChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private ArrayAdapter adapter;
    public static ArrayList<Request> display = new ArrayList<>();
    public static boolean show = false;
    public static String query = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ListView listView = (ListView) findViewById(R.id.theList);
        EditText theFilter = (EditText) findViewById(R.id.searchFilter);
        Log.d(TAG, "onCreate: Started.");


        query = "";
        // ARRAYLIST OF LOCATIONS
        ArrayList<String> locations = new ArrayList<>();
        locations.add("Changi City Point");
        locations.add("Senat's Kitchen");
        locations.add("SUTD Canteen");
        locations.add("Simei");
        locations.add("Timbre+");
        locations.add("Bedok Point");
        locations.add("Jewel Changi Airport");

        final ArrayList<Request> list = new ArrayList<>();

        adapter = new ArrayAdapter(this, R.layout.search_item_layout, locations);
        listView.setAdapter(adapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Request");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d(TAG, "Data Changed");
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Log.d(TAG, snapshot.toString());
                    list.add(snapshot.getValue(Request.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.addValueEventListener(listener);

        // Handling Click Events in ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                display.clear();
                Toast.makeText(SearchActivity.this, "Displaying " + locations.get(position) + " queries", Toast.LENGTH_SHORT).show();
//                Log.d(TAG, locations.get(position));
                for (Request request : list) {
                    if (request.order.location.equals(locations.get(position))) {
//                        Log.d("SearchActQueryReceive", request.toString());
                        display.add(request);
                    }
                }
                show = true;
                query = locations.get(position);
                Intent myIntent = new Intent(SearchActivity.this, BottomNavActivity.class);
                SearchActivity.this.startActivity(myIntent);

//                Log.d(TAG, display.toString());
            }
        });



        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (SearchActivity.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    // CHECK INTERNET CONNECTION
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}