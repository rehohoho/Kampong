package com.example.kampung.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kampung.R;
import com.example.kampung.models.Request;
import com.example.kampung.models.RequestAction;

import com.example.kampung.ui.userprofile.RequestAdapter;

import java.util.ArrayList;


public class HomeRequestAdapter extends RecyclerView.Adapter<HomeRequestAdapter.RequestViewHolder> {

    private final String TAG = "RequestAdapter";
    private final LayoutInflater mInflater;
    private ArrayList<Request> requests;
    private NavController navController;
    private String reqKey;

    public HomeRequestAdapter(Context context, ArrayList<Request> requests) {
        mInflater = LayoutInflater.from(context);
        this.requests = requests;
        //navController=navcontroller;

    }

    static class RequestViewHolder extends RecyclerView.ViewHolder{
        TextView locationTV,vendorTV,timeTV,destTV,teleHandleTV,userTV;


        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            locationTV = itemView.findViewById(R.id.text_requestlocation);
            vendorTV=itemView.findViewById(R.id.text_restaurant);
            timeTV=itemView.findViewById(R.id.text_reqtime_elapsed);

        }


    }

    static class HomeRequestDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView locationTV,vendorTV,timeTV,destTV,teleHandleTV,userTV;

        public HomeRequestDetailsViewHolder(@NonNull View itemView){
            super(itemView);
            locationTV = itemView.findViewById(R.id.reqdetails_location);
            vendorTV=itemView.findViewById(R.id.reqdetails_vendor);
            timeTV=itemView.findViewById(R.id.reqdetails_time);
            userTV=itemView.findViewById(R.id.reqdetails_user);
            destTV=itemView.findViewById(R.id.reqdetails_dest);
            teleHandleTV=itemView.findViewById(R.id.reqdetails_telehandle);

        }
    }

    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = mInflater.inflate(R.layout.fragment_home_request_cardview, viewGroup, false);
        /*itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("requestKey", view.);
                navController.navigate(R.id.action_navigation_home_to_home_req_details_Fragment,bundle);
            }
        });
*/
        return new RequestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        Request req = requests.get(position);
        holder.locationTV.setText(req.order.location);
        holder.vendorTV.setText(req.order.vendor);
        holder.timeTV.setText(req.time.toString()); // how to display the time properly from firebase

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_activity_bottom_nav);
                navController.navigate(R.id.action_navigation_home_to_home_req_details_Fragment);
                //NavHostFragment.findNavController(HomeFragment).navigate(R.id.action_navigation_home_to_home_req_details_Fragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }
}