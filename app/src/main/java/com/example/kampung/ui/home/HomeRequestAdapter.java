package com.example.kampung.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kampung.R;
import com.example.kampung.models.Request;
import com.example.kampung.models.RequestAction;

import java.util.ArrayList;


public class HomeRequestAdapter extends RecyclerView.Adapter<HomeRequestAdapter.RequestViewHolder> {

    private final String TAG = "RequestAdapter";
    private final LayoutInflater mInflater;
    private ArrayList<RequestAction> requests;

    public HomeRequestAdapter(Context context, ArrayList<RequestAction> requests) {
        mInflater = LayoutInflater.from(context);
        this.requests = requests;
    }

    static class RequestViewHolder extends RecyclerView.ViewHolder{
        TextView locationTV, vendorTV, timeTV;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            locationTV = itemView.findViewById(R.id.text_requestlocation);
            vendorTV = itemView.findViewById(R.id.text_restaurant);
            timeTV = itemView.findViewById(R.id.text_reqtime_elapsed);
        }
    }

    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = mInflater.inflate(R.layout.fragment_home_request_cardview, viewGroup, false);
        return new RequestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        Request req = requests.get(position).getRequest();
        holder.locationTV.setText(req.order.location);
        holder.vendorTV.setText(req.order.vendor);
        holder.timeTV.setText(req.getTimeInString()); // how to display the time properly from firebase

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_activity_bottom_nav);
                Bundle bundle = new Bundle();
                bundle.putParcelable("request", req);
                navController.navigate(R.id.action_navigation_home_to_home_req_details_Fragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }
}