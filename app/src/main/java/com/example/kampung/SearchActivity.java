package com.example.kampung;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.theList);
        EditText theFilter = (EditText) findViewById(R.id.searchFilter);
        Log.d(TAG, "onCreate: Started.");


        // ARRAYLIST OF LOCATIONS
        ArrayList<String> locations = new ArrayList<>();
        locations.add("Changi City Point");
        locations.add("Senat's Kitchen");
        locations.add("SUTD");
        locations.add("Simei");
        locations.add("Timbre+");
        locations.add("Bedok Point");
        locations.add("Jewel Changi Airport");


        adapter = new ArrayAdapter(this, R.layout.search_item_layout, locations);
        listView.setAdapter(adapter);


        // Handling Click Events in ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(SearchActivity.this, "Displaying " + locations.get(position) + " queries", Toast.LENGTH_SHORT).show();
            }

            // TODO: IMPLEMENT ONITEMCLICK TO SEND QUERY WHEN BUTTON CLICKED TO DISPLAY A FRAGMENT OF ALL REQUESTS WITHIN THE QUERIED LOCATION
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
}