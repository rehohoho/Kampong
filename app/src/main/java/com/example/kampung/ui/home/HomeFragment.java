package com.example.kampung.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kampung.R;
import com.example.kampung.databinding.FragmentHomeBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.example.kampung.models.Request;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View homeView =inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=homeView.findViewById(R.id.recycler_browsereq);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return homeView;
    }

    /*@Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions options=new FirebaseRecyclerOptions.Builder<Request>()
                .setQuery(reqlist,Request.class)
                .build();
        FirebaseRecyclerAdapter<Request,viewHolder> adapter =new FirebaseRecyclerAdapter<Request,viewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull Request model) {


                String reqid=getRef(position).getKey();
                reqlist.child(reqid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String location= snapshot.child("order").child("location").getValue().toString();
                        String vendor= snapshot.child("order").child("vendor").getValue().toString();
                        holder.reqlocation.setText(location);
                        holder.reqrestaurant.setText(vendor);

                        holder.setItemClickListener(new itemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean islongpress) {
                                Intent intent= new Intent(getActivity(), display_details.class);
                                startActivity(intent);



                            }
                        });


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                    }
                });

            }

            @NonNull
            @Override
            public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_request_cardview,parent,false);
                viewHolder viewHolder=new viewHolder(view);
                return viewHolder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }


*/


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}