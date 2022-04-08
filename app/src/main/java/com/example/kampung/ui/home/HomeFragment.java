package com.example.kampung.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kampung.R;
import com.example.kampung.controllers.DAO;
import com.example.kampung.controllers.RequestsViewModel;
import com.example.kampung.databinding.FragmentHomeBinding;
import com.example.kampung.models.RequestAction;
import com.example.kampung.ui.RequestAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;



import com.example.kampung.models.Request;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    private final String TAG = "HomeFragment";
    private RequestsViewModel requestsViewModel;
    private ArrayList<RequestAction> requestList = new ArrayList<>();
    private FragmentHomeBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*View homeView =inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=homeView.findViewById(R.id.recycler_browsereq);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return homeView;*/

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HomeRequestAdapter requestAdapter = new HomeRequestAdapter(getContext(), requestList);

        binding.recyclerBrowsereq.setAdapter(requestAdapter);

        binding.recyclerBrowsereq.setLayoutManager(new LinearLayoutManager(getContext()));


        requestsViewModel = new ViewModelProvider(this).get(RequestsViewModel.class);
        requestsViewModel.getRequests(DAO.getInstance()).observe(getViewLifecycleOwner(), request -> {
            Log.d(TAG, "nani " + request.toString());
            requestList.add(request);
            requestAdapter.notifyItemInserted(requestList.size() - 1);
        });


        /*// handles navigation to Dabao Fragment
        //*binding.buttonOrder.setOnClickListener(view1 ->
                NavHostFragment.findNavController(MainFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment));
//**/
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